package io.github.sgpublic.aidescit.api.core.spring.security

import io.github.sgpublic.aidescit.api.core.util.JwtUtil
import io.github.sgpublic.aidescit.api.core.util.Log
import io.github.sgpublic.aidescit.api.exceptions.ServerRuntimeException
import io.github.sgpublic.aidescit.api.mariadb.domain.UserInfo
import io.github.sgpublic.aidescit.api.module.SessionModule
import io.github.sgpublic.aidescit.api.module.UserInfoModule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.GenericFilterBean
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest

/**
 * @author sgpublic
 * @date 2022/5/8 14:38
 */
@Component
class AidescitTokenFilter(
    private val session: SessionModule,
    private val info: UserInfoModule
): GenericFilterBean() {
    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        Log.d("AidescitTokenFilter.doFilter()")
        if (request is HttpServletRequest) {
            doFilter(request)
        }
        chain?.doFilter(request, response)
    }


    private val context: SecurityContext get() =
        SecurityContextHolder.getContext()
    private val guest = AidescitAuthenticationToken(AidescitAuthority.GUEST_AUTHORITY, "", UserInfo.GUEST)
    private fun doFilter(request: HttpServletRequest): Boolean {
        var accessToken: String? = null
        for (cookie: Cookie in request.cookies ?: arrayOf()) {
            if (cookie.name != "access_token") {
                continue
            }
            accessToken = cookie.value
        }
        val context = SecurityContextHolder.getContext()
        if (accessToken == null) {
            if (!context.authentication.isAuthenticated) {
                context.authentication = guest
                return false
            }
            throw ServerRuntimeException.INTERNAL_ERROR
        }
        checkToken(accessToken)
        return false
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