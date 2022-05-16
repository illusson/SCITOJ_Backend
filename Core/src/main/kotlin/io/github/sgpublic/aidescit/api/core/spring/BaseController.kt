package io.github.sgpublic.aidescit.api.core.spring

import io.github.sgpublic.aidescit.api.core.spring.security.AidescitAuthenticationToken
import io.github.sgpublic.aidescit.api.mariadb.domain.UserInfo
import org.springframework.security.core.context.SecurityContextHolder

abstract class BaseController {
    protected val auth: AidescitAuthenticationToken get() {
        return SecurityContextHolder.getContext()
            .authentication as AidescitAuthenticationToken
    }

    protected val user: UserInfo get() {
        val authentication = auth.authorities.toTypedArray()[0]
        return authentication as UserInfo
    }
}