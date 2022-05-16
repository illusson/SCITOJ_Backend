package io.github.illusson.scitoj.admin.controller

import io.github.sgpublic.aidescit.api.core.spring.BaseController
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * @author sgpublic
 * @date 2022/5/5 13:27
 */
@RestController("adminProblemController")
@RequestMapping("/api/problem")
class ProblemController: BaseController() {
    @PostMapping("/create")
    fun createProblem(
        @RequestParam(name = "access_token") token: String,
        @RequestParam(name = "display_id") displayId: String,
        title: String): Map<String, Any?> {
        TODO("Not yet implemented")
    }

    @PostMapping("/edit")
    fun editProblem(
        @RequestParam(name = "access_token") token: String,
        @RequestParam(name = "id") pId: String,
        @RequestParam(name = "display_id", required = false) displayId: String? = null,
        @RequestParam(name = "sample", required = false) sample: String? = null,
        @RequestParam(name = "show_guest", required = false) showGuest: Boolean? = null,
        @RequestParam(name = "show_public", required = false) showPublic: Boolean? = null,
        @RequestParam(required = false) title: String? = null,
        @RequestParam(required = false) solution: String? = null,
    ): Map<String, Any?> {
        TODO("Not yet implemented")
    }
}