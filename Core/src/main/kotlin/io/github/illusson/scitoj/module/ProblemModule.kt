package io.github.illusson.scitoj.module

import io.github.illusson.scitoj.core.util.findByIdAuthed
import io.github.illusson.scitoj.dto.request.ListPageDto
import io.github.illusson.scitoj.mariadb.dao.ProblemDetailRepository
import io.github.illusson.scitoj.mariadb.dao.ProblemRepository
import io.github.illusson.scitoj.mariadb.dao.ProblemTagRepository
import io.github.illusson.scitoj.mariadb.dao.TagChartRepository
import io.github.illusson.scitoj.mariadb.domain.*
import io.github.sgpublic.aidescit.api.core.base.CurrentUser
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
            problem.findByIdAuthed(id),
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
}