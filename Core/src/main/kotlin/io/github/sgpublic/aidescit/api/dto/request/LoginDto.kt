package io.github.sgpublic.aidescit.api.dto.request

import io.github.sgpublic.aidescit.api.dto.BaseRequestDto

data class LoginDto(
    val username: String,
    val password: String,
    val ticket: Long
): BaseRequestDto()