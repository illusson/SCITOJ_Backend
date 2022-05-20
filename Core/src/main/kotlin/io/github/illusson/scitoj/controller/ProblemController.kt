package io.github.illusson.scitoj.controller

import io.github.illusson.scitoj.dto.request.CreateProblemDto
import io.github.illusson.scitoj.dto.request.EditProblemDto
import io.github.illusson.scitoj.dto.request.ListProblemDto
import io.github.illusson.scitoj.dto.request.SubmitSolutionDto
import io.github.illusson.scitoj.dto.response.ProblemCreateDto
import io.github.illusson.scitoj.dto.response.ProblemDetailDto
import io.github.illusson.scitoj.dto.response.ProblemListDto
import io.github.illusson.scitoj.mariadb.dao.ProblemRepository
import io.github.illusson.scitoj.mariadb.domain.Problem
import io.github.sgpublic.aidescit.api.core.base.BaseController
import io.github.sgpublic.aidescit.api.core.spring.annotation.ApiGetMapping
import io.github.sgpublic.aidescit.api.core.spring.annotation.ApiPostMapping
import io.github.sgpublic.aidescit.api.core.spring.security.AidescitAuthority
import io.github.sgpublic.aidescit.api.dto.BaseResponseDto
import io.github.sgpublic.aidescit.api.exceptions.ProblemNotFoundException
import io.swagger.v3.oas.annotations.tags.Tag
import org.springdoc.api.annotations.ParameterObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.RestController
import java.util.*

/**
 * @author sgpublic
 * @date 2022/5/5 13:27
 */
@Tag(name = "题目类", description = "题目操作")
@RestController
class ProblemController: BaseController() {
    @Autowired
    private lateinit var problem: ProblemRepository

    @ApiGetMapping("/api/problem/list")
    fun listProblem(@ParameterObject page: ListProblemDto): ProblemListDto {
        val list = if (IS_AUTHENTICATED && CURRENT_USER.IS_ADMIN) {
            problem.listForAdmin(
                page.pageIndex, page.pageSize, page.order
            )
        } else {
            problem.listForPublic(
                page.pageIndex, page.pageSize, IS_AUTHENTICATED, page.order
            )
        }
        return ProblemListDto(list)
    }

    @ApiGetMapping("/api/problem/detail")
    fun getProblemDetail(pId: Int): ProblemDetailDto {
        val p = getProblemById(pId)
        return ProblemDetailDto(p)
    }

    @PreAuthorize(AidescitAuthority.AUTHORIZE_UP_USER)
    @ApiPostMapping("/api/problem/submit")
    fun submitSolution(submit: SubmitSolutionDto): BaseResponseDto {
        TODO("not implement yet")
    }

    @PreAuthorize(AidescitAuthority.AUTHORIZE_UP_ADMIN)
    @ApiPostMapping("/api/problem/create")
    fun createProblem(create: CreateProblemDto): ProblemCreateDto {
        Problem().also {
            it.createUser = CURRENT_USER.username
            it.title = create.title
            it.displayId = create.displayId
        }.let {
            return ProblemCreateDto(problem.save(it).id)
        }
    }

    @PreAuthorize(AidescitAuthority.AUTHORIZE_UP_ADMIN)
    @ApiPostMapping("/api/problem/edit")
    fun editProblem(edit: EditProblemDto): BaseResponseDto {
        val p = getProblemById(edit.pId)
        edit.displayId?.let { p.displayId = it }
        edit.title?.let { p.title = it }
        p.description = edit.description
        edit.sample?.let { p.sample = it }
        edit.showGuest?.let { p.showGuest = it }
        edit.showPublic?.let { p.showPublic = it }
        p.editUser = CURRENT_USER.username
        p.editTime = Date()
        problem.save(p)
        return BaseResponseDto()
    }

    private fun getProblemById(id: Int): Problem {
        return problem.findByIdOrNull(id)?.takeIf {
            if (!IS_AUTHENTICATED) {
                return@takeIf it.showPublic && it.showGuest
            }
            if (CURRENT_USER.IS_ADMIN) {
                return@takeIf true
            }
            return@takeIf it.showPublic
        } ?: throw ProblemNotFoundException()
    }
}