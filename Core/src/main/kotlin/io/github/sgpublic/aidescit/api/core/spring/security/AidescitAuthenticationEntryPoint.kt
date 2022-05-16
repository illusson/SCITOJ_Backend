package io.github.sgpublic.aidescit.api.core.spring.security

import io.github.sgpublic.aidescit.api.core.util.Log
import io.github.sgpublic.aidescit.api.core.util.writeJson
import io.github.sgpublic.aidescit.api.exceptions.WrongPasswordException
import io.github.sgpublic.aidescit.api.result.FailedResult
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author sgpublic
 * @date 2022/5/7 15:55
 */
class AidescitAuthenticationEntryPoint: AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        Log.d("登录失败", authException)
        val out = when (authException) {
            is WrongPasswordException -> {
                FailedResult.WRONG_ACCOUNT
            }
            else -> {
                FailedResult.ANONYMOUS_DENIED
            }
        }
        response.writeJson(out)
    }
}