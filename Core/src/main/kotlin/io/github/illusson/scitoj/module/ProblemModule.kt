package io.github.illusson.scitoj.module

import io.github.illusson.scitoj.dto.request.ListPageDto
import io.github.illusson.scitoj.mariadb.dao.ProblemDetailRepository
import io.github.illusson.scitoj.mariadb.dao.ProblemRepository
import io.github.illusson.scitoj.mariadb.dao.ProblemTagRepository
import io.github.illusson.scitoj.mariadb.dao.TagChartRepository
import io.github.illusson.scitoj.mariadb.domain.*
import io.github.sgpublic.aidescit.api.core.base.CurrentUser
import io.github.sgpublic.aidescit.api.exceptions.ProblemNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.util.*

@Component
class ProblemModule {
    @Autowired
    private lateinit var problem: ProblemRepository
    @Autowired
    private lateinit var detail: ProblemDetailRepository
    @Autowired
    private lateinit var tags: ProblemTagRepository
    @Autowired
    private lateinit var tagChart: TagChartRepository

    fun listProblem(page: ListPageDto, tag: List<Int>? = null): List<SampleProblem> {
        tag ?: return problem.listForPublic(
            page.start, page.size, CurrentUser.IS_AUTHENTICATED, page.order
        )
        val pid = tags.getProblemByTags(tag)
        return problem.listPublicProblemIn(pid, page.start, page.size, CurrentUser.IS_AUTHENTICATED, page.order)
    }

    fun getProblemDetail(id: Int): Triple<Problem, ProblemDetail?, List<TagChart>> {
        return Triple(
            getProblemById(id),
            detail.findByIdOrNull(id),
            tagChart.getTagIn(tags.getTagsByProblem(id))
        )
    }

    fun submitSolution(pid: Int, code: String, type: String): Boolean {
        TODO("not implement yet")
    }

    fun listProblemForAdmin(page: ListPageDto, tag: List<Int>? = null): List<Problem> {
        tag ?: return problem.listForAdmin(
            page.start, page.size, page.order
        )
        val pid = tags.getProblemByTags(tag)
        return problem.listAdminProblemIn(pid, page.start, page.size, page.order)
    }

    fun createProblem(title: String): Int {
        Problem().also {
            it.createUser = CurrentUser.USER_INFO.username
            it.title = title
        }.let {
            return problem.save(it).id
        }
    }

    fun editProblem(
        pid: Int, description: String,
        sample: ArrayList<ProblemDetail.Sample>? = null,
        hint: String? = null,
        showGuest: Boolean? = null, showPublic: Boolean? = null,
        title: String? = null, tagList: List<String> = listOf()
    ): Boolean {
        val p = getProblemById(pid)
        title?.let { p.title = it }
        showPublic?.let { p.showPublic = it }
        showGuest?.let {
            p.showGuest = it
            if (it) p.showPublic = true
        }
        p.editUser = CurrentUser.USERNAME
        p.editTime = Date()

        val d = ProblemDetail().also { detail ->
            detail.id = pid
            detail.description = description
            sample?.let { detail.sample = it }
            hint?.let { detail.hint = it }
        }

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

        detail.save(d)
        tags.saveAll(list)
        problem.save(p)
        return true
    }

    private fun getProblemById(id: Int): Problem {
        return problem.findByIdOrNull(id)?.takeIf {
            if (!CurrentUser.IS_AUTHENTICATED) {
                return@takeIf it.showPublic && it.showGuest
            }
            if (CurrentUser.USER_INFO.IS_ADMIN) {
                return@takeIf true
            }
            return@takeIf it.showPublic
        } ?: throw ProblemNotFoundException()
    }
}