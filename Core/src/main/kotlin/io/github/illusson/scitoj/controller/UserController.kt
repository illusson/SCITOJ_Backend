package io.github.illusson.scitoj.controller

import io.github.sgpublic.aidescit.api.core.spring.annotation.ApiPostMapping
import io.github.sgpublic.aidescit.api.core.spring.security.AidescitAuthority
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController {
    @PreAuthorize(AidescitAuthority.AUTHORIZE_UP_GOD)
    @ApiPostMapping("/admin/user/role")
    fun setUserRole() {

    }
}