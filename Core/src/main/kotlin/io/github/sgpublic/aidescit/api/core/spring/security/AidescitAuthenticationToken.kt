package io.github.sgpublic.aidescit.api.core.spring.security

import io.github.sgpublic.aidescit.api.mariadb.domain.UserInfo
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class AidescitAuthenticationToken(
    private val username: String, private val password: String,
    val authentication: UserInfo? = null
) : AbstractAuthenticationToken(
    arrayListOf<GrantedAuthority>().also { array ->
        authentication?.let { array.add(it) }
    }
) {
    init {
        super.setAuthenticated(authentication != null)
    }

    override fun getDetails(): UserInfo? {
        return authentication
    }

    /**
     * 认证信息，即密码
     */
    override fun getCredentials(): String {
        return password
    }

    /**
     *
     */
    override fun getPrincipal(): String {
        return username
    }

    override fun getName(): String {
        return username
    }

    override fun setAuthenticated(isAuthenticated: Boolean) {
        require(!isAuthenticated) { "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead" }
        super.setAuthenticated(false)
    }
}