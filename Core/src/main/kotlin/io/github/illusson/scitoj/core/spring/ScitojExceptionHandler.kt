package io.github.illusson.scitoj.core.spring

import io.github.illusson.scitoj.exceptions.ItemEditException
import io.github.sgpublic.aidescit.api.dto.FailedResult
import io.github.sgpublic.aidescit.api.exceptions.ProblemNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ScitojExceptionHandler {
    /** 容错处理，请求题目不存在错误拦截 */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ProblemNotFoundException::class)
    fun handleProblemNotFoundException(): FailedResult {
        return FailedResult.PROBLEM_NOTFOUND
    }

    /** 容错处理，请求题目不存在错误拦截 */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ItemEditException::class)
    fun handleItemEditException(e: ItemEditException): FailedResult {
        return FailedResult.ITEM_EDIT_ERROR.also {
            it.message += "，${e.message}"
        }
    }
}