package io.github.sgpublic.aidescit.api.dto

import io.swagger.v3.oas.annotations.media.Schema

open class BaseResponseDto(
    @Schema(description = "响应代码")
    open var code: Int = 200,
    @Schema(description = "响应信息")
    open var message: String = "success.",
) {
    fun messageCode(code: Int, message: String) {
        this.code = code
        this.message = message
    }

    companion object {
        fun new(code: Int, message: String): BaseResponseDto {
            return BaseResponseDto(code, message)
        }
    }
}