package io.github.sgpublic.aidescit.api.dto.request

import io.github.sgpublic.aidescit.api.dto.SignedRequest

data class LoginDto(
    val username: String,
    val password: String,
    val ticket: Long,

    override val ts: Long,
    override val sign: String
): SignedRequest