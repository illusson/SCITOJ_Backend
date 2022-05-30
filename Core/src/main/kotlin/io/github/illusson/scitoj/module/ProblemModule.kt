package io.github.illusson.scitoj.module

import io.github.illusson.scitoj.core.util.findByIdAuthed
import io.github.illusson.scitoj.dto.request.ProblemFilter
import io.github.illusson.scitoj.dto.response.ProblemDetailDto
import io.github.illusson.scitoj.mariadb.dao.*
import io.github.illusson.scitoj.mariadb.domain.*
import io.github.sgpublic.aidescit.api.core.base.CurrentUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.util.*
import kotlin.math.roundToInt

@Component
class ProblemModule {
    @Autowired
    private lateinit var problem: ProblemRepository
    @Autowired
    private lateinit var submit: ProblemSubmissionRepository
    @Autowired
    private lateinit var detail: ProblemDetailRepository
    @Autowired
    private lateinit var tags: ProblemTagRepository
    @Autowired
    private lateinit var tagChart: TagChartRepository

    fun createProblem(displayId: String, title: String) {
        Problem().also {
            it.displayId = displayId
            it.createUser = CurrentUser.USERNAME
            it.title = title
        }.let {
            problem.save(it)
        }
    }

    fun pageProblem(pageable: Pageable, filter: ProblemFilter): Page<SampleStatusProblem> {
        val (include, tagFilter, exclude) = checkFilter(filter)
        val find = problem.listPublic(
            pageable, CurrentUser.IS_AUTHENTICATED,
            include, tagFilter, exclude, filter.daily
        ).map {
            return@map SampleStatusProblem.of(
                it, getProblemStatus(it.displayId),
                getProblemPassingRate(it.displayId)
            )
        }
        return PageImpl(find.content, find.pageable, find.totalElements)
    }

    private fun checkFilter(filter: ProblemFilter): Triple<HashSet<String>?, HashSet<String>?, HashSet<String>?> {
        var include: HashSet<String>? = null
        var exclude: HashSet<String>? = null
        filter.state?.let { state ->
            val set = HashSet<String>()
            val all = submit.listByUid(CurrentUser.USERNAME)
            all?.forEach { set.add(it.pid) }
            if (state == ProblemDetailDto.StateParam.pending) {
                exclude = set
                return@let
            }
            if (state == ProblemDetailDto.StateParam.tried) {
                all?.forEach {
                    if (it.subStatus != ProblemSubmission.SubStatus.accepted) {
                        return@forEach
                    }
                    set.remove(it.pid)
                }
                include = set
                return@let
            }
            // state == ProblemDetailDto.StateParam.ac
            all?.forEach {
                if (it.subStatus != ProblemSubmission.SubStatus.accepted) {
                    return@forEach
                }
                set.add(it.pid)
            }
            include = set
        }
        val tagFilter = filter.tid?.let {
            val tmp = HashSet<String>()
            tmp.addAll(tags.getProblemByTags(it))
            return@let tmp
        }
        return Triple(include, tagFilter, exclude)
    }

    fun getProblemDetail(id: String): Triple<StatusProblem, ProblemDetail?, List<TagChart>> {
        val problem = problem.findByIdAuthed(id)
        return Triple(
            StatusProblem.of(problem, getProblemStatus(id), getProblemPassingRate(id)),
            detail.findByIdOrNull(id),
            tagChart.getTagIn(tags.getTagsByProblem(id))
        )
    }

    fun submitSolution(pid: String, code: String, type: ProblemSubmission.CodeType): Long {
        val id = ProblemSubmission().also {
            it.pid = pid
            it.subCode = code
            it.subCodeType = type
        }.let {
            submit.save(it).id
        }
        // TODO 通知判题机逻辑待完成
        return id
    }

    fun pageProblemForAdmin(pageable: Pageable, filter: ProblemFilter): Page<StatusProblem> {
        val (include, tagFilter, exclude) = checkFilter(filter)
        val find = problem.listAdmin(
            pageable, include, tagFilter, exclude, filter.daily
        ).map {
            return@map StatusProblem.of(
                it, getProblemStatus(it.displayId),
                getProblemPassingRate(it.displayId)
            )
        }
        return PageImpl(find.content, find.pageable, find.totalElements)
    }

    fun editProblem(
        pid: String, description: String,
        sample: List<ProblemDetail.Sample>? = null,
        hint: String? = null,
        showGuest: Boolean? = null, showPublic: Boolean? = null,
        title: String? = null, tagList: List<String> = listOf()
    ): Boolean {
        val p = problem.findByIdAuthed(pid)
        val d = ProblemDetail()

        title?.let { p.title = it }

        d.id = pid
        d.description = description
        sample?.let { d.sample = it }
        hint?.let { d.hint = it }

        val list = LinkedList<ProblemTags>()
        for (tag: String in tagList) {
            val tid = tagChart.findByName(tag).takeIf {
                it != null
            } ?: TagChart().also {
                it.name = tag
            }.let {
                return@let tagChart.save(it).id
            }
            list.add(ProblemTags().also {
                it.pid = pid
                it.tid = tid
            })
        }

        p.showGuest = showGuest == true
        p.showPublic = p.showGuest || showPublic == true
        p.editUser = CurrentUser.USERNAME
        p.editTime = Date()

        detail.save(d)
        tags.saveAll(list)
        problem.save(p)
        return true
    }

    fun getProblemStatus(pid: String): ProblemDetailDto.StateParam {
        val ac = submit.listByUidAndPid(CurrentUser.USERNAME, pid)
            .takeIf { !it.isNullOrEmpty() }?.any {
                it.subStatus == ProblemSubmission.SubStatus.accepted
            }
            ?: return ProblemDetailDto.StateParam.pending
        return ProblemDetailDto.StateParam.ac.takeIf { ac }
            ?: ProblemDetailDto.StateParam.tried
    }

    fun getProblemPassingRate(pid: String): Int {
        val list = submit.listByPid(pid)
        val total = list?.size?.toDouble() ?: return 0
        val ac = list.count {
            it.subStatus == ProblemSubmission.SubStatus.accepted
        }.toDouble()
        return (ac / total * 100).roundToInt()
    }
}