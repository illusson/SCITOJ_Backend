package io.github.sgpublic.aidescit.api.dto

import com.fasterxml.jackson.annotation.JsonInclude

/**
 * 处理失败结果封装
 * @param code 错误码
 * @param message 错误说明
 */
@JsonInclude(JsonInclude.Include.NON_ABSENT)
class FailedResult(code: Int, message: String): BaseResponseDto(code, message) {
    companion object {
        @JvmStatic
        val INVALID_SIGN = FailedResult(-400, "服务签名错误")
        @JvmStatic
        val SERVICE_EXPIRED = FailedResult(-400, "服务请求过期")
        @JvmStatic
        val UNSUPPORTED_REQUEST = FailedResult(-400, "不支持的请求方式")
        @JvmStatic
        val WRONG_ACCOUNT = FailedResult(-401, "账号或密码错误")
        @JvmStatic
        val EXPIRED_TOKEN = FailedResult(-402, "无效的 token")
        @JvmStatic
        val EMPTY_RESULT = FailedResult(-403, "数据为空")
        @JvmStatic
        val ANONYMOUS_DENIED = FailedResult(-405, "请登陆后再试")
        @JvmStatic
        val ACCESS_DENIED = FailedResult(-406, "非常抱歉，您暂时不能访问")
        @JvmStatic
        val EXPIRED_REFRESH_TOKEN = FailedResult(-404, "token 失效，请重新登陆")
        @JvmStatic
        val INTERNAL_SERVER_ERROR = FailedResult(-500, "服务器内部错误")
        @JvmStatic
        val SERVER_PROCESSING_ERROR = FailedResult(-500, "请求处理出错")
        @JvmStatic
        val NOT_IMPLEMENTATION_ERROR = FailedResult(-500, "别买炒饭了，头发快掉光了(´╥ω╥`)")

        @JvmStatic
        val PROBLEM_NOTFOUND = FailedResult(-1001, "您请求的题目不存在")
        @JvmStatic
        val ITEM_EDIT_ERROR = FailedResult(-1002, "对象信息编辑出错")
    }
}