package io.github.illusson.scitoj.dto.request

import io.github.sgpublic.aidescit.api.dto.SignedRequestDto
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.web.bind.annotation.RequestParam
import javax.persistence.Transient

open class ListPageDto(
    @Schema(description = "题目列表页数", required = true)
    val page: Int = 0,

    @Schema(description = "题目列表每页容量", defaultValue = "20")
    @RequestParam(required = false, defaultValue = "20")
    val size: Int = 20,

    @Schema(description = "题目列表排序规则", defaultValue = "20",
        allowableValues = ["desc", "asc"])
    @RequestParam(name = "order", required = false, defaultValue = "desc")
    val order: String = "desc"
): SignedRequestDto() {

    @get:Transient
    val start: Int get() = page * size
}