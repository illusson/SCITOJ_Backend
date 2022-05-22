package io.github.sgpublic.aidescit.api.core.base

import io.github.sgpublic.aidescit.api.core.spring.security.AidescitAuthenticationToken
import io.github.sgpublic.aidescit.api.mariadb.domain.UserInfo
import org.springframework.security.core.context.SecurityContextHolder

object CurrentUser {
    val IS_AUTHENTICATED: Boolean get() {
        return SecurityContextHolder.getContext()
            .authentication != null
    }

    val AUTHENTICATION: AidescitAuthenticationToken
        get() {
            return SecurityContextHolder.getContext()
                .authentication as AidescitAuthenticationToken
        }

    val USER_INFO: UserInfo
        get() {
            val authentication = AUTHENTICATION.authorities.toTypedArray()[0]
            return authentication as UserInfo
        }

    val USERNAME: String get() = USER_INFO.username
}