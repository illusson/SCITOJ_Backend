package io.github.sgpublic.aidescit.api.core.spring.security

import io.github.sgpublic.aidescit.api.module.APIModule
import io.github.sgpublic.aidescit.api.module.SessionModule
import io.github.sgpublic.aidescit.api.module.UserInfoModule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component

/**
 * @author sgpublic
 * @date 2022/5/5 13:04
 */
@Component
class AidescitAuthenticationProvider: AuthenticationProvider {
    @Autowired
    private lateinit var session: SessionModule

    @Autowired
    private lateinit var info: UserInfoModule

    override fun authenticate(authentication: Authentication): Authentication {
        try {
            authentication as AidescitAuthenticationToken
            val username = authentication.principal
            val password = authentication.credentials
            val ts = authentication.ts
            session.get(username, password, ts)
            return AidescitAuthenticationToken(
                username, password, info.get(username), APIModule.TS_FULL
            )
        } catch (e: Exception) {
            throw BadCredentialsException("登录失败", e)
        }
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return authentication == AidescitAuthenticationToken::class.java
    }
}