package io.github.sgpublic.aidescit.api.controller

import io.github.sgpublic.aidescit.api.core.base.BaseController
import io.github.sgpublic.aidescit.api.core.spring.security.AidescitAuthority
import io.github.sgpublic.aidescit.api.dto.response.UserInfoDto
import io.github.sgpublic.aidescit.api.mariadb.dao.ClassChartRepository
import io.github.sgpublic.aidescit.api.mariadb.dao.FacultyChartRepository
import io.github.sgpublic.aidescit.api.mariadb.dao.SpecialtyChartRepository
import io.github.sgpublic.aidescit.api.mariadb.domain.UserInfo
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "用户信息类", description = "获取用户信息")
@RestController
class UserInfoController: BaseController() {
    @Autowired
    private lateinit var classChart: ClassChartRepository
    @Autowired
    private lateinit var facultyChart: FacultyChartRepository
    @Autowired
    private lateinit var specialtyChart: SpecialtyChartRepository

    @Operation(summary = "获取用户接口", description = "用于获取当前登录用户信息。")
    @PreAuthorize(AidescitAuthority.AUTHORIZE_UP_USER)
    @GetMapping("/aidescit/info", consumes = ["application/x-www-form-urlencoded"])
    fun getUserInfo(): UserInfoDto {
        return UserInfoDto(UserInfoDto.Info(
            name = CURRENT_USER.name,
            nickname = CURRENT_USER.nickname,
            identify = UserInfoDto.Description(
                id = CURRENT_USER.identify,
                desc = UserInfo.IDENTIFIES[CURRENT_USER.identify],
            ),
            role = UserInfoDto.Description(
                id = CURRENT_USER.role,
                desc = AidescitAuthority.AUTHORITIES[CURRENT_USER.role]!!
            ),
            faculty = facultyChart.getFacultyName(CURRENT_USER.faculty),
            specialty = specialtyChart.getSpecialtyName(
                CURRENT_USER.faculty, CURRENT_USER.specialty
            ),
            `class` = classChart.getClassName(
                CURRENT_USER.faculty, CURRENT_USER.specialty, CURRENT_USER.classId, CURRENT_USER.grade
            ),
            grade = CURRENT_USER.grade.toString()
        ))
    }
}