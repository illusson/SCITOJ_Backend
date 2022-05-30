package io.github.illusson.scitoj.controller

import io.github.illusson.scitoj.dto.request.CreateProblemDto
import io.github.illusson.scitoj.dto.request.EditProblemDto
import io.github.illusson.scitoj.dto.request.PagedListProblemDto
import io.github.illusson.scitoj.dto.request.SubmitSolutionDto
import io.github.illusson.scitoj.dto.response.IdDto
import io.github.illusson.scitoj.dto.response.PagedDto
import io.github.illusson.scitoj.dto.response.ProblemDetailDto
import io.github.illusson.scitoj.mariadb.domain.SampleStatusProblem
import io.github.illusson.scitoj.mariadb.domain.StatusProblem
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
@Tag(name = "题目类")
@RestController
class ProblemController {
    @Autowired
    private lateinit var problem: ProblemModule

    @Operation(summary = "用户题库", description = "主页展示的题库列表，支持可选参数筛选。")
    @ApiGetMapping("/api/problem/list")
    fun listProblem(@ParameterObject page: PagedListProblemDto): PagedDto<SampleStatusProblem> {
        val list = problem.pageProblem(page.pageable, page)
        return PagedDto.of(list)
    }

    @Operation(summary = "用户题目详情", description = "向普通用户展示的题目详情。")
    @ApiGetMapping("/api/problem/detail")
    fun getProblemDetail(id: String): ProblemDetailDto<StatusProblem> {
        val (problem, detail, tag) = problem.getProblemDetail(id)
        return ProblemDetailDto(problem, detail, tag)
    }

    @Operation(summary = "用户代码提交", description = "提交代码，请提交 UTF-8 编码、由 base64 加密后的文本")
    @PreAuthorize(AidescitAuthority.AUTHORIZE_UP_USER)
    @ApiPostMapping("/api/problem/submit")
    fun submitSolution(submit: SubmitSolutionDto): BaseResponseDto {
        val id = problem.submitSolution(submit.pid, submit.codeContent, submit.codeType)
        return IdDto(id)
    }

    @Operation(summary = "管理员题目管理", description = "管理页展示的题库列表，包含额外信息。")
    @PreAuthorize(AidescitAuthority.AUTHORIZE_UP_ADMIN)
    @ApiGetMapping("/admin/problem/list")
    fun listProblemForAdmin(@ParameterObject page: PagedListProblemDto): PagedDto<StatusProblem> {
        val list = problem.pageProblemForAdmin(page.pageable, page)
        return PagedDto.of(list)
    }

    @Operation(summary = "管理员题目创建", description = "管理页创建题目，通过标题创建题目，返回题目 ID 用于进一步编辑。")
    @PreAuthorize(AidescitAuthority.AUTHORIZE_UP_ADMIN)
    @ApiPostMapping("/admin/problem/create")
    fun createProblem(create: CreateProblemDto): BaseResponseDto {
        problem.createProblem(create.id, create.title)
        return BaseResponseDto()
    }

    @Operation(summary = "管理员题目编辑", description = "管理页编辑题目，若设置题目向游客公开，则默认同时向登录用户公开。")
    @PreAuthorize(AidescitAuthority.AUTHORIZE_UP_ADMIN)
    @ApiPostMapping("/admin/problem/edit")
    fun editProblem(edit: EditProblemDto): BaseResponseDto {
        problem.editProblem(
            edit.pid, edit.description, edit.sample,
            edit.hint, edit.showGuest, edit.showPublic,
            edit.title, edit.tag ?: listOf()
        )
        return BaseResponseDto()
    }

    @Operation(summary = "管理页题目详情", description = "管理页题目详情，包含额外信息。")
    @ApiGetMapping("/admin/problem/detail")
    fun getProblemDetailForAdmin(id: String): ProblemDetailDto<StatusProblem> {
        val (info, tag) = problem.getProblemDetail(id)
        return ProblemDetailDto(info, tag)
    }
}