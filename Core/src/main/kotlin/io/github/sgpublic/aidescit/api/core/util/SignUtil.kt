package io.github.sgpublic.aidescit.api.core.util

import io.github.illusson.scitoj.Application
import io.github.sgpublic.aidescit.api.core.spring.property.SignProperty
import io.github.sgpublic.aidescit.api.exceptions.InvalidSignException
import io.github.sgpublic.aidescit.api.exceptions.ServiceExpiredException
import io.github.sgpublic.aidescit.api.module.APIModule
import okhttp3.internal.toLongOrDefault
import org.springframework.context.annotation.DependsOn
import org.springframework.stereotype.Component

/**
 * sign 计算工具封装
 */
@Component
class SignUtil {
    companion object {
        @DependsOn("signKeysRepository")
        fun calculate(map: Map<String, Array<String>>){
            if (!map.containsKey("sign")){
                Log.d("未提交 sign")
                return
            }
            val sortedMap = ArgumentReader.readRequestMap(map)
            val string = StringBuilder()
            for ((key, value) in sortedMap) {
                if (key == "sign"){
                    continue
                }
                if (string.isNotEmpty()){
                    string.append("&")
                }
                string.append("$key=$value")
            }

            if (!Application.DEBUG){
                val ts = sortedMap["ts"].toString()
                    .toLongOrDefault(0)
                    .minus(APIModule.TS)
                if (ts > 600 || ts < -30){
                    throw ServiceExpiredException()
                }
            }

            string.append(SignProperty.APP_SECRET)
            val sign = MD5Util.encodeFull(string.toString())
            if (sign != sortedMap["sign"]){
                throw InvalidSignException(sign, sortedMap["sign"] ?: "empty")
            }
        }
    }
}