package io.github.illusson.scitoj.dto.request

import io.github.sgpublic.aidescit.api.core.base.CurrentUser
import io.github.sgpublic.aidescit.api.dto.SignedRequest

data class User(
    val username: String = CurrentUser.USERNAME,

    override val ts: Long,
    override val sign: String
): SignedRequest

data class UserRole(
    val username: String = CurrentUser.USERNAME,
    val role: String,

    override val ts: Long,
    override val sign: String
): SignedRequest