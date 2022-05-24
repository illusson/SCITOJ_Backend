package io.github.sgpublic.aidescit.api.dto.request

import io.github.sgpublic.aidescit.api.dto.SignedRequestDto

data class LoginDto(
    val username: String,
    val password: String,
    val ticket: Long
): SignedRequestDto()