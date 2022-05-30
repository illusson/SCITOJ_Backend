package io.github.illusson.scitoj.dto.response

import io.github.illusson.scitoj.mariadb.domain.ProblemDetail
import io.github.illusson.scitoj.mariadb.domain.SimpleItem
import io.github.illusson.scitoj.mariadb.domain.TagChart
import io.github.sgpublic.aidescit.api.dto.BaseResponseDto

data class ProblemDetailDto<T: SimpleItem>(
    val info: T? = null,
    val detail: ProblemDetail? = null,
    val tags: List<TagChart> = listOf()
): BaseResponseDto() {
    @Suppress("EnumEntryName")
    enum class StateParam(
        val tag: String
    ) {
        ac("ac"),
        tried("tried"),
        pending("pending"),
    }
}