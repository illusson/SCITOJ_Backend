package io.github.illusson.scitoj.dto.request

import io.github.illusson.scitoj.mariadb.domain.ProblemDetail
import io.github.sgpublic.aidescit.api.dto.SignedRequestDto
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.web.bind.annotation.RequestParam

data class CreateProblemDto(
    @Schema(description = "题目标题", required = true)
    var title: String
): SignedRequestDto()

data class EditProblemDto(
    @Schema(name = "id", required = true)
    @RequestParam(name = "id")
    var pid: Int,

    @Schema(name = "desc", required = true)
    @RequestParam(name = "desc")
    var description: String,

    @Schema(required = true)
    @RequestParam(name = "difficulty")
    var difficulty: Int,

    @RequestParam(name = "sample", required = false)
    var sample: List<ProblemDetail.Sample>? = null,

    @RequestParam(name = "hint", required = false)
    var hint: String? = null,

    @Schema(name = "show_guest")
    @RequestParam(name = "show_guest", required = false)
    var showGuest: Boolean? = null,

    @Schema(name = "show_public")
    @RequestParam(name = "show_public", required = false)
    var showPublic: Boolean? = null,

    @RequestParam(required = false)
    var title: String? = null,

    @Schema(name = "tag")
    @RequestParam(name = "tag", required = false)
    var tag: List<String> = listOf()
): SignedRequestDto()

data class SubmitSolutionDto(
    @Schema(name = "p_id")
    @RequestParam(name = "p_id")
    var pid: Int,

    @Schema(name = "code_content")
    @RequestParam(name = "code_content")
    var codeContent: String,

    @Schema(name = "code_type")
    @RequestParam(name = "code_type")
    var codeType: String
): SignedRequestDto()