package io.github.sgpublic.aidescit.api.core.spring.security

import io.github.sgpublic.aidescit.api.core.base.BaseOncePerRequestFilter
import io.github.sgpublic.aidescit.api.core.spring.RepeatableHttpServletRequest
import io.github.sgpublic.aidescit.api.core.util.JwtUtil
import io.github.sgpublic.aidescit.api.exceptions.ServerRuntimeException
import io.github.sgpublic.aidescit.api.module.SessionModule
import io.github.sgpublic.aidescit.api.module.UserInfoModule
import io.github.sgpublic.aidescit.api.dto.FailedResult
import io.jsonwebtoken.JwtException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import javax.servlet.FilterChain
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

/**
 * @author sgpublic
 * @date 2022/5/8 14:38
 */
class AidescitTokenFilter(
    private val session: SessionModule,
    private val info: UserInfoModule
): BaseOncePerRequestFilter() {
    override fun doFilter(
        request: RepeatableHttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ): Boolean {
        var accessToken: String? = null
        for (cookie: Cookie in request.cookies ?: arrayOf()) {
            if (cookie.name != "access_token") {
                continue
            }
            accessToken = cookie.value
        }
        val context = SecurityContextHolder.getContext()
        if (accessToken == null) {
            if (context.authentication != null &&
                !context.authentication.isAuthenticated) {
                throw ServerRuntimeException.INTERNAL_ERROR
            }
            return false
        }
        checkToken(accessToken)
        return false
    }

    override fun onException(e: Exception): Any {
        return when (e) {
            is JwtException -> FailedResult.EXPIRED_TOKEN
            else -> super.onException(e)
        }
    }

    @Autowired
    private lateinit var token: JwtUtil
    private fun checkToken(src: String) {
        val username = token.check(src)
        val session = session.get(username)
        val info = info.get(username)
        context.authentication = AidescitAuthenticationToken(username, session.password, info)
    }
}