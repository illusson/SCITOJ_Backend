package io.github.sgpublic.aidescit.api.core.spring.interceptor

import io.github.Application
import io.github.sgpublic.aidescit.api.core.util.SignUtil
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

/**
 * 过滤器，用于 sign 校验，DEBUG 环境下不校验
 */
class SignFilter: Filter {
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        if (!Application.DEBUG && request is HttpServletRequest){
            SignUtil.calculate(request)
        }
        chain.doFilter(request, response)
    }
}