package io.github.sgpublic.aidescit.api.dto.response

import io.github.sgpublic.aidescit.api.dto.BaseResponseDto

data class AccessTokenDto(
    val access_token: String
): BaseResponseDto()