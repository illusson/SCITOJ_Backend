package io.github.sgpublic.aidescit.api.dto.response

import io.github.sgpublic.aidescit.api.dto.BaseResponseDto
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "公钥响应参数")
data class PublicKeyDto(
    @Schema(description = "公钥内容")
    val key: String,
    @Schema(description = "密码加盐")
    val hash: String,
    @Schema(description = "登录票据")
    val ticket: Long
): BaseResponseDto()