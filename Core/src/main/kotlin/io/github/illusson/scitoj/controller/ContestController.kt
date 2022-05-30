package io.github.illusson.scitoj.controller

import io.github.illusson.scitoj.dto.request.CreateContest
import io.github.illusson.scitoj.dto.request.EditContest
import io.github.illusson.scitoj.module.ContestModule
import io.github.sgpublic.aidescit.api.core.spring.annotation.ApiPostMapping
import io.github.sgpublic.aidescit.api.core.spring.security.AidescitAuthority
import io.github.sgpublic.aidescit.api.dto.BaseResponseDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.RestController

@Tag(name = "竞赛管理类")
@RestController
class ContestController {
    @Autowired
    private lateinit var contest: ContestModule

    @Operation(summary = "管理员竞创建", description = "管理页创建竞赛，通过标题创建竞赛，返回竞赛 ID 用于进一步编辑。")
    @PreAuthorize(AidescitAuthority.AUTHORIZE_UP_ADMIN)
    @ApiPostMapping("/admin/contest/create")
    fun create(create: CreateContest): BaseResponseDto {
        contest.create(create.id, create.title)
        return BaseResponseDto()
    }

    @Operation(summary = "管理员竞赛编辑", description = "管理页编辑竞赛，若设置竞赛向游客公开，则默认同时向登录用户公开，需至少添加一题才能公开。")
    @PreAuthorize(AidescitAuthority.AUTHORIZE_UP_ADMIN)
    @ApiPostMapping("/admin/contest/edit")
    fun edit(edit: EditContest): BaseResponseDto {
        contest.edit(
            edit.cid, edit.description, edit.startTime, edit.duration,
            edit.showPublic, edit.showGuest, edit.title
        )
        return BaseResponseDto()
    }
}