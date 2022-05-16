package io.github.sgpublic.aidescit.api.controller

import io.github.sgpublic.aidescit.api.core.spring.BaseController
import io.github.sgpublic.aidescit.api.core.spring.security.AidescitAuthority
import io.github.sgpublic.aidescit.api.mariadb.dao.ClassChartRepository
import io.github.sgpublic.aidescit.api.mariadb.dao.FacultyChartRepository
import io.github.sgpublic.aidescit.api.mariadb.dao.SpecialtyChartRepository
import io.github.sgpublic.aidescit.api.mariadb.domain.UserInfo
import io.github.sgpublic.aidescit.api.result.SuccessResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserInfoController: BaseController() {
    @Autowired
    private lateinit var classChart: ClassChartRepository
    @Autowired
    private lateinit var facultyChart: FacultyChartRepository
    @Autowired
    private lateinit var specialtyChart: SpecialtyChartRepository

    @PreAuthorize("hasAnyAuthority(\"${AidescitAuthority.USER_AUTHORITY}\", \"${AidescitAuthority.ADMIN_AUTHORITY}\", \"${AidescitAuthority.GOD_AUTHORITY}\")")
    @RequestMapping("/aidescit/info")
    fun getUserInfo(): Map<String, Any?> {
        return SuccessResult(
            "info" to mapOf(
                "name" to user.name,
                "nickname" to user.nickname,
                "identify" to mapOf(
                    "id" to user.identify,
                    "desc" to UserInfo.IDENTIFIES[user.identify]
                ),
                "role" to mapOf(
                    "id" to user.role,
                    "desc" to AidescitAuthority.AUTHORITIES[user.role]
                ),
                "faculty" to facultyChart.getFacultyName(user.faculty),
                "specialty" to specialtyChart.getSpecialtyName(
                    user.faculty, user.specialty
                ),
                "class" to classChart.getClassName(
                    user.faculty, user.specialty, user.classId, user.grade
                ),
                "grade" to user.grade.toString()
            )
        )
    }
}