package io.github.sgpublic.aidescit.api.exceptions

import java.util.*
import javax.servlet.ServletException

/**
 * 服务签名错误，用户提交 sign 参数校验失败时抛出。
 */
class InvalidSignException(local: String? = null, submit: String? = null): ServletException(
    StringJoiner("，")
        .add("服务签名错误，校验：$local")
        .also {  joiner ->
            submit?.let { joiner.add("提交：$submit") }
        }
        .also {  joiner ->
            local?.let { joiner.add("校验：$submit") }
        }
        .toString()
)