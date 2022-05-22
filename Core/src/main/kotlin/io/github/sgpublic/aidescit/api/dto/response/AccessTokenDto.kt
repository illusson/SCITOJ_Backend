package io.github.sgpublic.aidescit.api.dto.response

import io.github.sgpublic.aidescit.api.dto.BaseResponseDto
import io.swagger.v3.oas.annotations.media.Schema

data class AccessTokenDto(
    @Schema(name = "access_token")
    val accessToken: String
): BaseResponseDto()