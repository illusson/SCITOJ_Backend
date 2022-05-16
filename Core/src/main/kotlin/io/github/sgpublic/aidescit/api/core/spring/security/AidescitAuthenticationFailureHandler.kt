package io.github.sgpublic.aidescit.api.core.spring.security

import io.github.sgpublic.aidescit.api.core.util.Log
import io.github.sgpublic.aidescit.api.core.util.writeJson
import io.github.sgpublic.aidescit.api.exceptions.InvalidPasswordFormatException
import io.github.sgpublic.aidescit.api.exceptions.WrongPasswordException
import io.github.sgpublic.aidescit.api.result.FailedResult
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author sgpublic
 * @date 2022/5/5 13:04
 */
@Component
class AidescitAuthenticationFailureHandler: AuthenticationFailureHandler {
    override fun onAuthenticationFailure(
        request: HttpServletRequest,
        response: HttpServletResponse,
        exception: AuthenticationException
    ) {
        Log.d("登陆失败", exception)
        when(exception) {
            is WrongPasswordException -> {
                response.writeJson(FailedResult.WRONG_ACCOUNT)
            }
            is InvalidPasswordFormatException -> {
                response.writeJson(FailedResult.WRONG_ACCOUNT)
            }
            else -> {
                response.writeJson(FailedResult.INTERNAL_SERVER_ERROR)
            }
        }
    }
}