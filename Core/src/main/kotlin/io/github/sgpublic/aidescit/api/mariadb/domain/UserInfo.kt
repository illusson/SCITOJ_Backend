package io.github.sgpublic.aidescit.api.mariadb.domain

import io.github.sgpublic.aidescit.api.core.spring.security.AidescitAuthority
import io.github.sgpublic.aidescit.api.data.ClassInfo
import io.github.sgpublic.aidescit.api.module.APIModule
import org.springframework.security.core.GrantedAuthority
import javax.persistence.*

/**
 * 数据表 user_info
 */
@Entity
@Table(name = "user_info")
class UserInfo: ClassInfo(), GrantedAuthority {
    @Id
    @Column(name = "u_id")
    var username: String = ""

    @Column(name = "u_nickname")
    var nickname: String? = null

    @Column(name = "u_name")
    var name: String = ""

    @Column(name = "u_identify")
    var identify: Int = 0

    @Column(name = "u_role")
    var role: String = AidescitAuthority.GUEST_AUTHORITY

    @Column(name = "u_faculty")
    override var faculty: Int = 0

    @Column(name = "u_specialty")
    override var specialty: Int = 0

    @Column(name = "u_class")
    override var classId: Short = 0

    @Column(name = "u_grade")
    override var grade: Short = 0

    @Column(name = "u_info_expired")
    var expired: Long = APIModule.TS + 1296000

    @Transient
    fun isTeacher(): Boolean {
        return identify.compareTo(0) != 0
    }

    @Transient
    fun isStudent(): Boolean {
        return identify.compareTo(0) == 0
    }

    @Transient
    fun isExpired(): Boolean = expired < APIModule.TS

    @Transient
    override fun getAuthority(): String {
        return role
    }

    companion object {
        @JvmStatic
        val GUEST: UserInfo get() = UserInfo()

        @JvmStatic
        val IDENTIFIES = arrayOf("同学", "老师")
    }

//    @Transient
//    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
//        return mutableListOf(this)
//    }
//
//    @Transient
//    override fun getPassword(): String = ""
//
//    @Transient
//    override fun getUsername(): String = uname
//
//    @Transient
//    override fun isAccountNonExpired(): Boolean =
//        expired > APIModule.TS
//
//    @Transient
//    override fun isAccountNonLocked(): Boolean = false
//
//    @Transient
//    override fun isCredentialsNonExpired(): Boolean = false
//
//    @Transient
//    override fun isEnabled(): Boolean = true
}