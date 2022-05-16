package io.github.sgpublic.aidescit.api.core.spring.security

import io.github.sgpublic.aidescit.api.core.util.writeJson
import io.github.sgpublic.aidescit.api.result.SuccessResult
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AidescitLogoutSuccessHandler: LogoutSuccessHandler {
    override fun onLogoutSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        response.writeJson(SuccessResult.LOGOUT)
    }
}