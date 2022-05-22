package io.github.sgpublic.aidescit.api.controller

import io.github.sgpublic.aidescit.api.core.base.CurrentUser
import io.github.sgpublic.aidescit.api.core.spring.annotation.ApiGetMapping
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
import org.springframework.web.bind.annotation.RestController

@Tag(name = "用户信息类", description = "获取用户信息")
@RestController
class UserInfoController {
    @Autowired
    private lateinit var classChart: ClassChartRepository
    @Autowired
    private lateinit var facultyChart: FacultyChartRepository
    @Autowired
    private lateinit var specialtyChart: SpecialtyChartRepository

    @Operation(summary = "获取用户接口", description = "用于获取当前登录用户信息。")
    @PreAuthorize(AidescitAuthority.AUTHORIZE_UP_USER)
    @ApiGetMapping("/aidescit/info")
    fun getUserInfo(): UserInfoDto {
        val current = CurrentUser.USER_INFO
        return UserInfoDto(UserInfoDto.Info(
            name = current.name,
            nickname = current.nickname,
            identify = UserInfoDto.Description(
                id = current.identify,
                desc = UserInfo.IDENTIFIES[current.identify],
            ),
            role = UserInfoDto.Description(
                id = current.role,
                desc = AidescitAuthority.AUTHORITIES[current.role]!!
            ),
            faculty = facultyChart.getFacultyName(current.faculty),
            specialty = specialtyChart.getSpecialtyName(
                current.faculty, current.specialty
            ),
            `class` = classChart.getClassName(
                current.faculty, current.specialty, current.classId, current.grade
            ),
            grade = current.grade.toString()
        ))
    }
}