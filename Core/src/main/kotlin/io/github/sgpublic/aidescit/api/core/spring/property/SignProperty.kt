package io.github.sgpublic.aidescit.api.core.spring.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

/**
 * 注入 sign.properties
 */
@Component
@ConfigurationProperties(prefix = "aidescit.sign")
class SignProperty {
    companion object {
        private lateinit var appSecret: String

        @JvmStatic
        val APP_SECRET: String get() = appSecret
    }

    fun setAppSecret(value: String) {
        appSecret = value
    }
}