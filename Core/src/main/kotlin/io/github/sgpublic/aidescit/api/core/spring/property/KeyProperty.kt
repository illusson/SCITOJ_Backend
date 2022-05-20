package io.github.sgpublic.aidescit.api.core.spring.property

import io.github.sgpublic.aidescit.api.core.util.Base64Util
import io.github.sgpublic.aidescit.api.core.util.Log
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.InvalidKeySpecException
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec

/**
 * 注入 key.properties
 */
@Component
@ConfigurationProperties(prefix = "aidescit.rsa")
class KeyProperty {
    companion object {
        private lateinit var privateKey: PrivateKey
        private lateinit var publicKeyStr: String
        private lateinit var publicKey: PublicKey

        @JvmStatic
        val PRIVATE_KEY: PrivateKey get() = privateKey
        val PUBLIC_KEY_STRING: String get() = publicKeyStr
        val PUBLIC_KEY: PublicKey get() = publicKey
    }

    fun setPrivate(value: String){
        try {
            val decoded = Base64Util.decode(value)
            val key = KeyFactory.getInstance("RSA")
            privateKey = key.generatePrivate(PKCS8EncodedKeySpec(decoded))
        } catch (e: IllegalArgumentException){
            Log.f("请将 aidescit.rsa.private 直接设置为 RSA 私钥内容", e)
        } catch (e: InvalidKeySpecException){
            Log.f("请将 aidescit.rsa.private 设置为正确的 RSA 私钥", e)
        }
    }

    fun setPublic(value: String){
        try {
            val decoded = Base64Util.decode(value)
            val key = KeyFactory.getInstance("RSA")
            publicKey = key.generatePublic(X509EncodedKeySpec(decoded))
            publicKeyStr = value
        } catch (e: IllegalArgumentException){
            Log.f("请将 aidescit.rsa.public 直接设置为 RSA 公钥内容", e)
        } catch (e: InvalidKeySpecException){
            Log.f("请将 aidescit.rsa.public 设置为正确的 RSA 共钥", e)
        }
    }
}