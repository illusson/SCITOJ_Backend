package io.github.illusson.scitoj.user.controller

import io.github.sgpublic.aidescit.api.core.spring.BaseController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * @author sgpublic
 * @date 2022/5/5 12:24
 */
@RestController("userProblemController")
@RequestMapping("/api/problem")
class ProblemController: BaseController()  {
    @RequestMapping("/submit")
    fun submitSolution(
        @RequestParam(name = "p_id") pId: Int,
        @RequestParam(name = "code_content") codeContent: String,
        @RequestParam(name = "code_type") codeType: String): Map<String, Any?> {
        TODO("not implement yet")
    }
}