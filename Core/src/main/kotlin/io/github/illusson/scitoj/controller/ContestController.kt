package io.github.illusson.scitoj.controller

import io.github.illusson.scitoj.dto.request.CreateContestDto
import io.github.illusson.scitoj.dto.request.EditContestDto
import io.github.illusson.scitoj.dto.response.IdDto
import io.github.illusson.scitoj.module.ContestModule
import io.github.sgpublic.aidescit.api.core.spring.annotation.ApiPostMapping
import io.github.sgpublic.aidescit.api.core.spring.security.AidescitAuthority
import io.github.sgpublic.aidescit.api.dto.BaseResponseDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.RestController

@RestController
class ContestController {
    @Autowired
    private lateinit var contest: ContestModule

    @PreAuthorize(AidescitAuthority.AUTHORIZE_UP_ADMIN)
    @ApiPostMapping("/admin/contest/create")
    fun create(create: CreateContestDto): IdDto {
        val id = contest.create(create.title)
        return IdDto(id)
    }

    @PreAuthorize(AidescitAuthority.AUTHORIZE_UP_ADMIN)
    @ApiPostMapping("/admin/contest/edit")
    fun edit(edit: EditContestDto): BaseResponseDto {
        contest.edit(
            edit.cid, edit.description, edit.startTime,
            edit.duration, edit.showPublic, edit.showGuest,
            edit.title
        )
        return BaseResponseDto()
    }
}