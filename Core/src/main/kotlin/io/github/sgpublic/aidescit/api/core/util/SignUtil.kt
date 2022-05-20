package io.github.sgpublic.aidescit.api.core.util

import io.github.sgpublic.aidescit.api.core.spring.RepeatableHttpServletRequest
import io.github.sgpublic.aidescit.api.core.spring.property.SignProperty
import io.github.sgpublic.aidescit.api.exceptions.InvalidSignException
import io.github.sgpublic.aidescit.api.exceptions.ServerRuntimeException
import io.github.sgpublic.aidescit.api.exceptions.ServiceExpiredException
import io.github.sgpublic.aidescit.api.module.APIModule
import okhttp3.internal.toLongOrDefault
import org.springframework.boot.configurationprocessor.json.JSONObject
import java.util.*

/**
 * 检查请求签名，仅适用于 [io.github.sgpublic.aidescit.api.core.spring.RepeatableHttpServletRequest]
 */
fun RepeatableHttpServletRequest.checkSign() {
    when {
        contentType.contains("application/json") -> {
            calculateJsonBodySign()
        }
        contentType.contains("application/x-www-form-urlencoded") -> {
            calculateFormBodySign()
        }
        else -> throw ServerRuntimeException.UNKNOWN_CONTENT_TYPE
    }
}

/** 检查 FormData 的签名 */
private fun RepeatableHttpServletRequest.calculateFormBodySign() {
    val map = formBody()
    if (!map.containsKey("sign")){
        throw InvalidSignException()
    }

    val time = map["ts"].toString()
        .toLongOrDefault(0)
        .minus(APIModule.TS)
    if (time > 600 || time < -30){
        throw ServiceExpiredException()
    }

    val string = StringJoiner("${requestURI}?",
        "&", SignProperty.APP_SECRET)
    for ((key, value) in map) {
        if (key == "sign"){
            continue
        }
        string.add("$key=$value")
    }

    val sign = string.toString().MD5_FULL
    if (sign != map["sign"]){
        throw InvalidSignException(sign, map["sign"])
    }
}

/** 检查 Json 的签名 */
private fun RepeatableHttpServletRequest.calculateJsonBodySign() {
    val json = jsonBody()
    if (json.isNull("sign")) {
        throw InvalidSignException()
    }

    val time = json.getLong("ts") - APIModule.TS
    if (time > 600 || time < -30){
        throw ServiceExpiredException()
    }

    val sign = json.getString("sign")
    json.put("sign", SignProperty.APP_SECRET)
    val check = JSONObject().also {
        it.put("url", requestURI)
        it.put("data", json)
    }.toString().MD5_FULL
    if (sign != check) {
        throw InvalidSignException(sign, check)
    }
}