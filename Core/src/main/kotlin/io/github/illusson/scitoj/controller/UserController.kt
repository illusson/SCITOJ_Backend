package io.github.illusson.scitoj.controller

import io.github.illusson.scitoj.dto.request.User
import io.github.illusson.scitoj.dto.request.UserRole
import io.github.sgpublic.aidescit.api.core.spring.annotation.ApiGetMapping
import io.github.sgpublic.aidescit.api.core.spring.annotation.ApiPostMapping
import io.github.sgpublic.aidescit.api.core.spring.security.AidescitAuthority
import io.github.sgpublic.aidescit.api.dto.BaseResponseDto
import io.github.sgpublic.aidescit.api.dto.response.UserInfoDto
import io.github.sgpublic.aidescit.api.mariadb.dao.ClassChartRepository
import io.github.sgpublic.aidescit.api.mariadb.dao.FacultyChartRepository
import io.github.sgpublic.aidescit.api.mariadb.dao.SpecialtyChartRepository
import io.github.sgpublic.aidescit.api.mariadb.domain.getInfo
import io.github.sgpublic.aidescit.api.module.UserInfoModule
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springdoc.api.annotations.ParameterObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.RestController

@Tag(name = "用户信息类")
@RestController
class UserController {
    @Autowired
    private lateinit var info: UserInfoModule
    @Autowired
    private lateinit var classChart: ClassChartRepository
    @Autowired
    private lateinit var facultyChart: FacultyChartRepository
    @Autowired
    private lateinit var specialtyChart: SpecialtyChartRepository

    @Operation(summary = "用户角色设置接口", description = "用于修改用户角色。")
    @PreAuthorize(AidescitAuthority.AUTHORIZE_UP_GOD)
    @ApiPostMapping("/admin/user/role")
    fun setUserRole(role: UserRole): BaseResponseDto {
        info.setUserRule(role.username, role.role)
        return BaseResponseDto()
    }

    @Operation(summary = "其他用户信息接口", description = "用于获取其他用户信息。")
    @PreAuthorize(AidescitAuthority.AUTHORIZE_UP_ADMIN)
    @ApiGetMapping("/api/user/info")
    fun getUserInfo(@ParameterObject user: User): UserInfoDto {
        val current = info.get(user.username)
        return UserInfoDto(current.getInfo(
            facultyChart, specialtyChart, classChart
        ))
    }
}