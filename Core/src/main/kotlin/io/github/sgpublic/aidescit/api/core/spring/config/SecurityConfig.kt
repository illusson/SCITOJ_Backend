package io.github.sgpublic.aidescit.api.core.spring.config

import io.github.Application
import io.github.sgpublic.aidescit.api.core.spring.filter.SignFilter
import io.github.sgpublic.aidescit.api.core.spring.security.*
import io.github.sgpublic.aidescit.api.module.SessionModule
import io.github.sgpublic.aidescit.api.module.UserInfoModule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.context.NullSecurityContextRepository

/**
 * @author sgpublic
 * @date 2022/5/5 13:03
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig: WebSecurityConfigurerAdapter() {
    private val whiteList: Array<String> = arrayOf(
        "/favicon.ico",
        "/openapi/**",

        "/aidescit/public_key",
        "/api/problem/list"
    )

    override fun configure(http: HttpSecurity) {
        http.cors().disable()
            .also {
                if (Application.DEBUG) {
                    it.csrf().disable()
                }
            }

        http.authorizeRequests()
            .antMatchers(*whiteList).permitAll()
            .anyRequest().authenticated()

        // 自定义登录处理和 Token 识别
        http.addFilterBefore(processingFilter(), UsernamePasswordAuthenticationFilter::class.java)
            .addFilterAfter(tokenFilter(), AidescitAuthenticationProcessingFilter::class.java)
            .addFilterBefore(signFilter(), AidescitAuthenticationProcessingFilter::class.java)

        // 自定义登出
        http.logout()
            .logoutUrl("/aidescit/logout")
            .deleteCookies("access_token")
            .logoutSuccessHandler(AidescitLogoutSuccessHandler())

        // 禁用 Session 机制
        http.sessionManagement().disable()
            .securityContext()
            .securityContextRepository(NullSecurityContextRepository())

        // 自定义认证错误事件
        http.exceptionHandling()
            .authenticationEntryPoint(AidescitAuthenticationEntryPoint())
            .accessDeniedHandler(AidescitAccessDeniedHandler())
    }

    @Autowired
    private lateinit var session: SessionModule
    @Autowired
    private lateinit var info: UserInfoModule
    private fun tokenFilter(): AidescitTokenFilter {
        return AidescitTokenFilter(session, info)
    }

    @Autowired
    private lateinit var successHandler: AidescitAuthenticationSuccessHandler
    @Autowired
    private lateinit var failedHandler: AidescitAuthenticationFailureHandler
    @Autowired
    private lateinit var provider: AidescitAuthenticationProvider
    private fun processingFilter(): AidescitAuthenticationProcessingFilter {
        return AidescitAuthenticationProcessingFilter(successHandler, failedHandler, provider)
    }

    private fun signFilter(): SignFilter {
        return SignFilter()
    }
}