package io.github.illusson.scitoj.controller

import io.github.illusson.scitoj.dto.request.*
import io.github.illusson.scitoj.dto.response.ProblemCreateDto
import io.github.illusson.scitoj.dto.response.ProblemDetailDto
import io.github.illusson.scitoj.dto.response.ProblemListDto
import io.github.illusson.scitoj.mariadb.domain.Problem
import io.github.illusson.scitoj.mariadb.domain.SampleProblem
import io.github.illusson.scitoj.module.ProblemModule
import io.github.sgpublic.aidescit.api.core.spring.annotation.ApiGetMapping
import io.github.sgpublic.aidescit.api.core.spring.annotation.ApiPostMapping
import io.github.sgpublic.aidescit.api.core.spring.security.AidescitAuthority
import io.github.sgpublic.aidescit.api.dto.BaseResponseDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springdoc.api.annotations.ParameterObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.RestController

/**
 * @author sgpublic
 * @date 2022/5/5 13:27
 */
@Tag(name = "题目类", description = "题目操作")
@RestController
class ProblemController {
    @Autowired
    private lateinit var problem: ProblemModule

    @Operation(summary = "用户题库", description = "主页展示的题库列表。")
    @ApiGetMapping("/api/problem/list")
    fun listProblem(@ParameterObject page: ListPageDto): ProblemListDto<SampleProblem> {
        val list = problem.listProblem(page)
        return ProblemListDto(list)
    }

    @Operation(summary = "用户题库", description = "主页展示的题库列表，按标签筛选。")
    @ApiPostMapping("/api/problem/list")
    fun listProblemByTag(page: TagProblemDto): ProblemListDto<SampleProblem> {
        val list = problem.listProblem(page, page.tid)
        return ProblemListDto(list)
    }

    @Operation(summary = "用户题目详情", description = "向普通用户展示的题目详情。")
    @ApiGetMapping("/api/problem/detail")
    fun getProblemDetail(id: Int): ProblemDetailDto<SampleProblem> {
        val (info, detail, tag) = problem.getProblemDetail(id)
        return ProblemDetailDto(info, detail, tag)
    }

    @Operation(summary = "用户代码提交", description = "提交代码，请提交 UTF-8 编码、由 base64 加密后的文本")
    @PreAuthorize(AidescitAuthority.AUTHORIZE_UP_USER)
    @ApiPostMapping("/api/problem/submit")
    fun submitSolution(submit: SubmitSolutionDto): BaseResponseDto {
        problem.submitSolution(submit.pid, submit.codeContent, submit.codeType)
        return BaseResponseDto()
    }

    @Operation(summary = "管理员题目管理", description = "管理页展示的题库列表，包含额外信息。")
    @PreAuthorize(AidescitAuthority.AUTHORIZE_UP_ADMIN)
    @ApiGetMapping("/admin/problem/list")
    fun listProblemForAdmin(@ParameterObject page: ListPageDto): ProblemListDto<Problem> {
        val list = problem.listProblemForAdmin(page)
        return ProblemListDto(list)
    }

    @Operation(summary = "管理员题目管理", description = "管理页展示的题库列表，包含额外信息，按标签筛选。")
    @PreAuthorize(AidescitAuthority.AUTHORIZE_UP_ADMIN)
    @ApiPostMapping("/admin/problem/list")
    fun listProblemForAdmin(page: TagProblemDto): ProblemListDto<Problem> {
        val list = problem.listProblemForAdmin(page)
        return ProblemListDto(list)
    }

    @Operation(summary = "管理员题目创建", description = "管理页创建题目，通过标题创建题目，返回题目 ID 用于进一步编辑。")
    @PreAuthorize(AidescitAuthority.AUTHORIZE_UP_ADMIN)
    @ApiPostMapping("/admin/problem/create")
    fun createProblem(create: CreateProblemDto): ProblemCreateDto {
        val pid = problem.createProblem(create.title)
        return ProblemCreateDto(pid)
    }

    @Operation(summary = "管理员题目编辑", description = "管理页编辑题目，若设置题目向游客公开，则默认同时向登录用户公开。")
    @PreAuthorize(AidescitAuthority.AUTHORIZE_UP_ADMIN)
    @ApiPostMapping("/admin/problem/edit")
    fun editProblem(edit: EditProblemDto): BaseResponseDto {
        problem.editProblem(
            edit.pid, edit.description, edit.sample,
            edit.hint, edit.showGuest, edit.showPublic,
            edit.title, edit.tag
        )
        return BaseResponseDto()
    }

    @Operation(summary = "管理页题目详情", description = "管理页题目详情，包含额外信息。")
    @ApiGetMapping("/admin/problem/detail")
    fun getProblemDetailForAdmin(id: Int): ProblemDetailDto<Problem> {
        val (info, tag) = problem.getProblemDetail(id)
        return ProblemDetailDto(info, tag)
    }
}