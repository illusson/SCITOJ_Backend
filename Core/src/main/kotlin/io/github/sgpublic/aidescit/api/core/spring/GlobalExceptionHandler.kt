package io.github.sgpublic.aidescit.api.core.spring

import io.github.sgpublic.aidescit.api.core.util.Log
import io.github.sgpublic.aidescit.api.dto.FailedResult
import io.github.sgpublic.aidescit.api.exceptions.*
import okio.IOException
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.NoHandlerFoundException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/** 全局异常拦截器 */
@RestControllerAdvice
class GlobalExceptionHandler {
    /**
     * 拦截404，生产环境下跳转主页
     */
    @ExceptionHandler(NoHandlerFoundException::class)
    fun handleNoHandlerFoundException(exception: Exception, response: HttpServletResponse){
//        if (Application.DEBUG){
            response.status = HttpStatus.NOT_FOUND.value()
//            return
//        }
//        response.status = HttpStatus.FOUND.value()
//        response.setHeader("Location", "https://aidescit.sgpublic.xyz/")
    }

    /**
     * 服务 Sign 错误拦截
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(InvalidSignException::class)
    fun handleInvalidSignException(): FailedResult {
        return FailedResult.INVALID_SIGN
    }

    /**
     * 服务请求过期拦截
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ServiceExpiredException::class)
    fun handleServiceExpiredException(): FailedResult {
        return FailedResult.SERVICE_EXPIRED
    }

    /**
     * 用户密码错误拦截
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(WrongPasswordException::class)
    fun handleWrongPasswordException(): FailedResult {
        return FailedResult.WRONG_ACCOUNT
    }

    /**
     * 无效的 token 拦截
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(TokenExpiredException::class)
    fun handleTokenExpiredException(): FailedResult {
        return FailedResult.EXPIRED_TOKEN
    }

    /**
     * 容错处理，参数解析失败错误拦截
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(): FailedResult {
        return FailedResult.INTERNAL_SERVER_ERROR
    }

    /** 容错处理，请求题目不存在错误拦截 */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ProblemNotFoundException::class)
    fun handleProblemNotFoundException(): FailedResult {
        return FailedResult.PROBLEM_NOTFOUND
    }

    /** 容错处理，参数验证异常错误拦截 */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(): FailedResult {
        return FailedResult.INTERNAL_SERVER_ERROR
    }

    /** 容错处理，Content-Type 错误 */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(HttpMediaTypeNotSupportedException::class)
    fun handleHttpMediaTypeNotSupportedException(e: Exception): FailedResult {
        return FailedResult.UNSUPPORTED_REQUEST
    }

    /** 服务器非自身导致错误拦截 */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ServerRuntimeException::class)
    fun handleServerRuntimeException(e: Exception): FailedResult  {
        Log.w("拦截错误，${e.message}", e)
        return FailedResult.SERVER_PROCESSING_ERROR
    }

    /** 容错处理，[TODO] 拦截 */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(NotImplementedError::class)
    fun handleNotImplementedError(e: Exception): FailedResult  {
        Log.d("拦截错误，${e.message}", e)
        return FailedResult.NOT_IMPLEMENTATION_ERROR
    }

    /** 容错处理，服务器内部处理错误拦截 */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(IOException::class, ServiceUnavailableException::class)
    fun handleIOException(e: Exception): FailedResult  {
        Log.d("拦截错误，${e.message}", e)
        return FailedResult.SERVER_PROCESSING_ERROR
    }

    /** 拦截其余所有错误 */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception, request: HttpServletRequest): FailedResult  {
        Log.e("拦截错误，${e.message}", e)
        return FailedResult.INTERNAL_SERVER_ERROR
    }
}