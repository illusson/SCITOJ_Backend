package io.github.sgpublic.aidescit.api.dto

import io.swagger.v3.oas.annotations.media.Schema

open class BaseRequestDto(
    @Schema(description = "请求时间戳")
    open var ts: Long = -1,
    @Schema(description = "请求签名")
    open var sign: String? = null,
)