package io.github.sgpublic.aidescit.api.mariadb.domain

import io.github.sgpublic.aidescit.api.core.spring.security.AidescitAuthority
import io.github.sgpublic.aidescit.api.mariadb.dao.ClassChartRepository
import io.github.sgpublic.aidescit.api.mariadb.dao.FacultyChartRepository
import io.github.sgpublic.aidescit.api.mariadb.dao.SpecialtyChartRepository
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
    override var classId: Int = 0

    @Column(name = "u_grade")
    override var grade: Int = 0

    @Column(name = "u_info_expired")
    var expired: Long = APIModule.TS + 1296000

    @Transient
    fun isTeacher(): Boolean {
        return identify != 0
    }

    @Transient
    fun isStudent(): Boolean {
        return identify == 0
    }

    @get:Transient
    val IS_ADMIN: Boolean get() {
        return role == AidescitAuthority.ADMIN_AUTHORITY
                || role == AidescitAuthority.GUEST_AUTHORITY
    }

    @Transient
    fun isExpired(): Boolean = expired < APIModule.TS

    @Transient
    override fun getAuthority(): String {
        return "role_$role"
    }

    companion object {
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

/**
 * 可选参数封装
 * @param faculty 学院代码
 * @param specialty 专业代码
 * @param classId 班级代码
 */
@Suppress("KDocUnresolvedReference")
open class ClassInfo {
    open var faculty: Int = -1

    open var specialty: Int = -1

    open var grade: Int = -1

    open var classId: Int = -1

    fun isNull(): Boolean {
        return faculty < 0 || specialty < 0 || grade < 0 || classId < 0
    }
}

data class Info(
    val name: String,
    val nickname: String?,
    val identify: Description<Int>,
    val role: Description<String>,
    val faculty: String?,
    val specialty: String?,
    val `class`: String?,
    val grade: String?,
) {
    data class Description<T>(
        var id: T,
        var desc: String
    )
}

@Transient
fun UserInfo.getInfo(
    facultyChart: FacultyChartRepository,
    specialtyChart: SpecialtyChartRepository,
    classChart: ClassChartRepository
): Info {
    return Info(
        name = name,
        nickname = nickname,
        identify = Info.Description(
            id = identify,
            desc = UserInfo.IDENTIFIES[identify],
        ),
        role = Info.Description(
            id = role,
            desc = AidescitAuthority.AUTHORITIES[role]!!
        ),
        faculty = facultyChart.getFacultyName(faculty),
        specialty = specialtyChart.getSpecialtyName(
            faculty, specialty
        ),
        `class` = classChart.getClassName(
            faculty, specialty, classId, grade
        ),
        grade = grade.toString()
    )
}