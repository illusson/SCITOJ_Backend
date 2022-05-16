package io.github.sgpublic.aidescit.api.core.spring.security

import io.github.sgpublic.aidescit.api.core.util.JwtUtil
import io.github.sgpublic.aidescit.api.core.util.writeJson
import io.github.sgpublic.aidescit.api.module.APIModule
import io.github.sgpublic.aidescit.api.result.SuccessResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author sgpublic
 * @date 2022/5/5 13:04
 */
@Component
class AidescitAuthenticationSuccessHandler: AuthenticationSuccessHandler {
    @Autowired
    private lateinit var token: JwtUtil

    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        authentication as AidescitAuthenticationToken
        val token = token.create(
            authentication.principal, authentication.credentials
        )
        val cookie = APIModule.Cookies.Builder()
            .add(APIModule.Cookies.ACCESS_TOKEN, token)
            .build()

        response.setHeader("Set-Cookie", cookie.toString())
        response.writeJson(SuccessResult(
            "access_token" to token
        ))
    }
}