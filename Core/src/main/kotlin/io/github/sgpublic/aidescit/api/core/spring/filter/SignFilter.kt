package io.github.sgpublic.aidescit.api.core.spring.filter

import io.github.Application
import io.github.sgpublic.aidescit.api.core.base.BaseOncePerRequestFilter
import io.github.sgpublic.aidescit.api.core.spring.RepeatableHttpServletRequest
import io.github.sgpublic.aidescit.api.core.util.checkSign
import io.github.sgpublic.aidescit.api.dto.FailedResult
import io.github.sgpublic.aidescit.api.exceptions.ServerRuntimeException
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletResponse

/**
 * 过滤器，用于 sign 校验，DEBUG 环境下不校验
 */
class SignFilter: BaseOncePerRequestFilter() {
    override fun doFilter(
        request: RepeatableHttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ): Boolean {
        if (Application.DEBUG) {
            return false
        }

        if (request.requestURI.startsWith("/openapi")) {
            return false
        }

        request.checkSign()
        return false
    }

    override fun onException(e: Exception): Any {
        return when(e) {
            is ServerRuntimeException -> FailedResult.INVALID_SIGN
            else -> super.onException(e)
        }
    }
}