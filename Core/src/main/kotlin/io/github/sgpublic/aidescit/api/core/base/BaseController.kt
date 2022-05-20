package io.github.sgpublic.aidescit.api.core.base

import io.github.sgpublic.aidescit.api.core.spring.security.AidescitAuthenticationToken
import io.github.sgpublic.aidescit.api.mariadb.domain.UserInfo
import org.springframework.security.core.context.SecurityContextHolder

@Suppress("PropertyName")
abstract class BaseController {
    protected val IS_AUTHENTICATED: Boolean get() {
        return SecurityContextHolder.getContext()
            .authentication != null
    }

    protected val AUTHENTICATION: AidescitAuthenticationToken get() {
        return SecurityContextHolder.getContext()
            .authentication as AidescitAuthenticationToken
    }

    protected val CURRENT_USER: UserInfo get() {
        val authentication = AUTHENTICATION.authorities.toTypedArray()[0]
        return authentication as UserInfo
    }
}