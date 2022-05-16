package io.github.illusson.scitoj.common.controller

import io.github.sgpublic.aidescit.api.core.spring.BaseController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * @author sgpublic
 * @date 2022/5/5 12:24
 */
@RestController
@RequestMapping("/api/problem")
class ProblemController: BaseController()  {
    @RequestMapping("/list")
    fun listProblem(
        @RequestParam(name = "access_token", required = false) token: String? = null,
        @RequestParam(name = "page_index") pageIndex: Int,
        @RequestParam(name = "page_size", required = false) pageSize: Int = 20): Map<String, Any?> {
        TODO("not implement yet")
    }

    @RequestMapping("/detail")
    fun getProblemDetail(@RequestParam(name = "access_token", required = false)
                   token: String? = null, pId: Int): Map<String, Any?> {
        TODO("not implement yet")
    }
}