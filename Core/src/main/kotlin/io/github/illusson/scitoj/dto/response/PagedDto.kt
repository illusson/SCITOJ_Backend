package io.github.illusson.scitoj.dto.response

import io.github.sgpublic.aidescit.api.dto.BaseResponseDto
import org.springframework.data.domain.Page

class PagedDto<T> private constructor(
    val list: List<T>,
    val size: Int,
    val totalPage: Int,
    val totalSize: Long
): BaseResponseDto() {
    companion object {
        fun <T> of(page: Page<T>): PagedDto<T> {
            return PagedDto(
                list = page.content,
                size = page.size,
                totalPage = page.totalPages,
                totalSize = page.totalElements,
            )
        }
    }
}