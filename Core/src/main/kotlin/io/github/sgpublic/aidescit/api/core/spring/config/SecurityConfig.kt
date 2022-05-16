package io.github.sgpublic.aidescit.api.core.spring.config

import io.github.Application
import io.github.sgpublic.aidescit.api.core.spring.security.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
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
    @Autowired
    private lateinit var provider: AidescitAuthenticationProvider

    override fun configure(auth: AuthenticationManagerBuilder) {
        // 自定义登录处理
        auth.authenticationProvider(provider)
    }

    @Autowired
    private lateinit var tokenFilter: AidescitTokenFilter
    @Autowired
    private lateinit var processingFilter: AidescitAuthenticationProcessingFilter
    override fun configure(http: HttpSecurity) {
        http.cors().disable()
            .also {
                if (Application.DEBUG) {
                    it.csrf().disable()
                }
            }

        // 自定义登录处理和 Token 识别
        http.addFilterBefore(processingFilter, UsernamePasswordAuthenticationFilter::class.java)
            .addFilterAfter(tokenFilter, AidescitAuthenticationProcessingFilter::class.java)

        http.authorizeRequests()
            .anyRequest().authenticated()

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
}