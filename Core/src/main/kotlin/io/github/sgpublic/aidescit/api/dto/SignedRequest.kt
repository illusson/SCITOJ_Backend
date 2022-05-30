package io.github.sgpublic.aidescit.api.dto

import io.swagger.v3.oas.annotations.media.Schema

interface SignedRequest {
    @get:Schema(description = "请求时间戳", required = true)
    val ts: Long

    @get:Schema(description = "请求签名", required = true)
    val sign: String
}