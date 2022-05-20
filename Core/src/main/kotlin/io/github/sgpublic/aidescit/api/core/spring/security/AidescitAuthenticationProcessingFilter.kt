package io.github.sgpublic.aidescit.api.core.spring.security

import io.github.sgpublic.aidescit.api.core.util.readJson
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.web.util.ContentCachingRequestWrapper
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author sgpublic
 * @date 2022/5/5 15:07
 */
class AidescitAuthenticationProcessingFilter(
    successHandler: AidescitAuthenticationSuccessHandler,
    failedHandler: AidescitAuthenticationFailureHandler,
    provider: AidescitAuthenticationProvider
): AbstractAuthenticationProcessingFilter(
    AntPathRequestMatcher("/aidescit/login"),
    ProviderManager(provider)
) {
    init {
        super.setAuthenticationSuccessHandler(successHandler)
        super.setAuthenticationFailureHandler(failedHandler)
    }

    private val loginRequest = object {
        var username: String = ""
        var password: String = ""
        var ts: Long = -1
    }::class
    override fun attemptAuthentication(request: HttpServletRequest,
                                       response: HttpServletResponse): Authentication {
        if (request.method != "POST") {
            throw AuthenticationServiceException("Authentication method not supported: " + request.method)
        }

        ContentCachingRequestWrapper(request)
        val req = try {
            request.readJson(loginRequest)
        } catch (e: Exception) {
            throw BadCredentialsException("参数缺失", e)
        }
        if (req.username == "" || req.password == "") {
            throw BadCredentialsException("Empty username or password.")
        }
        val auth = AidescitAuthenticationToken(req.username, req.password, ts = req.ts)
        return authenticationManager.authenticate(auth)
    }
}