package io.github.illusson.scitoj.dto.request

import io.github.illusson.scitoj.dto.response.ProblemDetailDto
import io.github.illusson.scitoj.mariadb.domain.ProblemDetail
import io.github.illusson.scitoj.mariadb.domain.ProblemSubmission
import io.github.sgpublic.aidescit.api.dto.SignedRequest
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.core.convert.converter.Converter
import org.springframework.web.bind.annotation.RequestParam
import java.util.*
import javax.persistence.Convert

data class PagedListProblemDto(
    @Schema(description = "题目列表页数", required = true)
    override val page: Int = 0,

    @Schema(description = "题目列表每页容量", defaultValue = "20")
    @RequestParam(required = false, defaultValue = "20")
    override val size: Int = 20,

    @Schema(description = "题目列表排序规则，升序或降序。", defaultValue = "desc",
        allowableValues = ["desc", "asc"], implementation = String::class)
    @RequestParam(name = "order", required = false, defaultValue = "desc")
    override val order: SortedList.OrderParam? = null,

    @Schema(
        description = "题目列表排序依据，可按题目标题、完成状态、难度以及通过率排序。",
        allowableValues = ["difficulty", "ac", "title", "state"],
        implementation = String::class,
    )
    @RequestParam(name = "basis", required = false)
    override val basis: BasisParam? = null,

    @Schema(description = "按标签筛选", implementation = String::class)
    @RequestParam(required = false, defaultValue = "")
    @Convert(converter = String2TagsConvert::class)
    override val tid: List<Int>? = null,

    @Schema(description = "按每日一题筛选")
    @RequestParam(required = false)
    override val daily: Boolean? = null,

    @Schema(description = "按完成状态筛选", implementation = String::class,
        allowableValues = ["ac", "tried", "pending"])
    @RequestParam(required = false)
    override val state: ProblemDetailDto.StateParam? = null,

    override val ts: Long,
    override val sign: String,
): SortedList<PagedListProblemDto.BasisParam>, SignedRequest, ProblemFilter {
    @Suppress("EnumEntryName")
    enum class BasisParam {
        difficulty, ac, title, state
    }

    class String2TagsConvert: Converter<String, List<Int>?> {
        override fun convert(source: String): List<Int>? {
            if (source == "") return null
            val list = LinkedList<Int>()
            val tags = source.split(",")
            for (tag: String in tags) {
                list.add(tag.toIntOrNull() ?: continue)
            }
            return list
        }
    }
}

interface ProblemFilter {
    val tid: List<Int>?

    val daily: Boolean?

    val state: ProblemDetailDto.StateParam?
}

data class CreateProblemDto(
    @Schema(description = "题目 ID", required = true)
    val id: String,
    @Schema(description = "题目标题", required = true)
    val title: String,

    override val ts: Long,
    override val sign: String
): SignedRequest

data class EditProblemDto(
    @Schema(name = "id", required = true)
    @RequestParam(name = "id")
    val pid: String,

    @Schema(name = "desc", required = true)
    @RequestParam(name = "desc")
    val description: String,

    @Schema(required = true)
    @RequestParam(name = "difficulty")
    val difficulty: Int,

    @RequestParam(name = "sample", required = false)
    val sample: List<ProblemDetail.Sample>? = null,

    @RequestParam(name = "hint", required = false)
    val hint: String? = null,

    @Schema(name = "show_guest")
    @RequestParam(name = "show_guest", required = false)
    val showGuest: Boolean? = null,

    @Schema(name = "show_public")
    @RequestParam(name = "show_public", required = false)
    val showPublic: Boolean? = null,

    @RequestParam(required = false)
    val title: String? = null,

    @Schema(name = "tag")
    @RequestParam(name = "tag", required = false)
    val tag: List<String>? = null,

    override val ts: Long,
    override val sign: String
): SignedRequest

data class SubmitSolutionDto(
    @Schema(name = "p_id")
    @RequestParam(name = "p_id")
    val pid: String,

    @Schema(name = "code_content")
    @RequestParam(name = "code_content")
    val codeContent: String,

    @Schema(name = "code_type")
    @RequestParam(name = "code_type")
    val codeType: ProblemSubmission.CodeType,

    override val ts: Long,
    override val sign: String
): SignedRequest