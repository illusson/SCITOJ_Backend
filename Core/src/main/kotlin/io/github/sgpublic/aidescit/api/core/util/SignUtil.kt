package io.github.sgpublic.aidescit.api.core.util

import io.github.sgpublic.aidescit.api.core.spring.property.SignProperty
import io.github.sgpublic.aidescit.api.exceptions.InvalidSignException
import io.github.sgpublic.aidescit.api.exceptions.ServiceExpiredException
import io.github.sgpublic.aidescit.api.module.APIModule
import okhttp3.internal.toLongOrDefault
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest

/**
 * sign 计算工具封装
 */
@Component
class SignUtil {
    companion object {
        fun calculate(request: HttpServletRequest){
            val map = request.parameterMap
            if (!map.containsKey("sign")){
                throw InvalidSignException()
            }
            val sortedMap = ArgumentReader.readRequestMap(map)
            val string = StringBuilder()
            string.append(request.requestURI)
            for ((key, value) in sortedMap) {
                if (key == "sign"){
                    continue
                }
                if (string.isNotEmpty()){
                    string.append("&")
                }
                string.append("$key=$value")
            }

            sortedMap["ts"].toString()
                .toLongOrDefault(0)
                .minus(APIModule.TS).let {
                    if (it > 600 || it < -30){
                        throw ServiceExpiredException()
                    }
                }

            string.append(SignProperty.APP_SECRET)

            val sign = string.toString().MD5_FULL
            if (sign != sortedMap["sign"]){
                throw InvalidSignException(sign, sortedMap["sign"] ?: "empty")
            }
        }
    }
}