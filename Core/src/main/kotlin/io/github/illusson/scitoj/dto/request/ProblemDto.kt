package io.github.illusson.scitoj.dto.request

import io.github.illusson.scitoj.mariadb.domain.Problem
import io.github.sgpublic.aidescit.api.dto.BaseRequestDto
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.web.bind.annotation.RequestParam

data class CreateProblemDto(
    @Schema(description = "题目公示 ID")
    @RequestParam(name = "display_id", required = false)
    var displayId: String? = null,
    @Schema(description = "题目标题", required = true)
    var title: String
): BaseRequestDto()

data class EditProblemDto(
    @RequestParam(name = "id") var pId: Int,
    @RequestParam(name = "description") var description: String? = null,
    @RequestParam(name = "display_id", required = false) var displayId: String? = null,
    @RequestParam(name = "sample", required = false) var sample: ArrayList<Problem.Sample>? = null,
    @RequestParam(name = "show_guest", required = false) var showGuest: Boolean? = null,
    @RequestParam(name = "show_public", required = false) var showPublic: Boolean? = null,
    @RequestParam(required = false) var title: String? = null
): BaseRequestDto()

data class ListProblemDto(
    @Schema(description = "题目列表页数", required = true)
    @RequestParam(name = "page_index")
    var pageIndex: Int,
    @Schema(description = "题目列表每页容量", defaultValue = "20")
    @RequestParam(name = "page_size", required = false, defaultValue = "20")
    var pageSize: Int = 20,
    @Schema(description = "题目列表排序规则", defaultValue = "20",
        allowableValues = ["desc", "asc"])
    @RequestParam(name = "page_size", required = false, defaultValue = "desc")
    var order: String = "desc"
): BaseRequestDto()

data class SubmitSolutionDto(
    @RequestParam(name = "p_id") var pId: Int,
    @RequestParam(name = "code_content") var codeContent: String,
    @RequestParam(name = "code_type") var codeType: String
): BaseRequestDto()