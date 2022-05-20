package io.github.sgpublic.aidescit.api.core.spring.security

import org.springframework.security.core.GrantedAuthority

/**
 * @author sgpublic
 * @date 2022/5/8 14:50
 */
class AidescitAuthority private constructor(
    private val role: String
) : GrantedAuthority {
    override fun getAuthority(): String {
        return role
    }

    companion object {
        const val GUEST_AUTHORITY = "common"
        val GUEST = AidescitAuthority(GUEST_AUTHORITY)

        const val USER_AUTHORITY = "user"
        val USER = AidescitAuthority(USER_AUTHORITY)

        const val ADMIN_AUTHORITY = "admin"
        val ADMIN = AidescitAuthority(ADMIN_AUTHORITY)

        const val GOD_AUTHORITY = "god"
        val GOD = AidescitAuthority(GOD_AUTHORITY)

        val AUTHORITIES = mapOf(
            GUEST_AUTHORITY to "游客",
            USER_AUTHORITY to "用户",
            ADMIN_AUTHORITY to "管理员",
            GOD_AUTHORITY to "超级管理员",
        )

        const val AUTHORIZE_UP_USER = "hasAnyRole(\"$USER_AUTHORITY\", \"$ADMIN_AUTHORITY\", \"$GOD_AUTHORITY\")"
        const val AUTHORIZE_UP_ADMIN = "hasAnyRole(\"$ADMIN_AUTHORITY\", \"$GOD_AUTHORITY\")"
        const val AUTHORIZE_UP_GOD = "hasRole(\"$GOD_AUTHORITY\")"
    }
}