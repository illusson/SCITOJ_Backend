package io.github.sgpublic.aidescit.api.core.base

import io.github.sgpublic.aidescit.api.core.spring.RepeatableHttpServletRequest
import io.github.sgpublic.aidescit.api.core.util.Log
import io.github.sgpublic.aidescit.api.core.util.writeJson
import io.github.sgpublic.aidescit.api.dto.FailedResult
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

abstract class BaseOncePerRequestFilter: OncePerRequestFilter() {
    protected val context: SecurityContext get() = SecurityContextHolder.getContext()
    final override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val req = RepeatableHttpServletRequest(request)
        val err = try {
            doFilter(req, response, filterChain)
        } catch (e: Exception) {
            Log.w(e.message ?: "未知错误", e)
            response.writeJson(onException(e))
            true
        }
        if (err) return
        filterChain.doFilter(req, response)
    }

    abstract fun doFilter(request: RepeatableHttpServletRequest, response: HttpServletResponse, filterChain: FilterChain): Boolean
    open fun onException(e: Exception): Any {
        return FailedResult.INTERNAL_SERVER_ERROR
    }
}