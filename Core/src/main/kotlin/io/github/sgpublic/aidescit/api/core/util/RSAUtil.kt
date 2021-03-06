package io.github.sgpublic.aidescit.api.core.util

import io.github.sgpublic.aidescit.api.core.spring.property.KeyProperty
import io.github.sgpublic.aidescit.api.exceptions.InvalidPasswordFormatException
import org.springframework.context.annotation.DependsOn
import javax.crypto.Cipher

/**
 * RSA 简单封装，支持 RSA/ECB/PKCS1Padding
 */
@DependsOn("keyProperty")
object RSAUtil {
    @JvmStatic
    private val PRIVATE_CI: Cipher get() {
        val cp = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        cp.init(Cipher.DECRYPT_MODE, KeyProperty.PRIVATE_KEY)
        return cp
    }

    @JvmStatic
    val PUBLIC_CI: Cipher get() {
        val cp = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        cp.init(Cipher.ENCRYPT_MODE, KeyProperty.PUBLIC_KEY)
        return cp
    }

    /**
     * 使用私钥解密文本
     * @param src 需解密的文本
     * @return 返回解密的文本
     */
    fun decode(src: String): String {
        return decode(src, PRIVATE_CI)
    }

    /**
     * 使用指定密钥解密文本
     * @param src 需解密的文本
     * @param cp 指定密钥
     * @return 返回解密的文本
     */
    fun decode(src: String, cp: Cipher): String {
        return cp.doFinal(Base64Util.decode(src)).toString(Charsets.UTF_8)
    }

    /**
     * 使用私钥加密文本
     * @param src 需解密的文本
     * @return 返回解密的文本
     */
    fun encode(src: String): String {
        return Base64Util.encodeToString(PRIVATE_CI.doFinal(src.toByteArray(Charsets.UTF_8)))
    }

    /**
     * 使用指定密钥解密文本
     * @param src 需解密的文本
     * @return 返回解密的文本
     */
    fun encode(src: String, cp: Cipher): String {
        return Base64Util.encodeToString(cp.doFinal(src.toByteArray(Charsets.UTF_8)))
    }

    fun decodePassword(password: String): String {
        return decode(password).apply {
            if (length <= 8){
                throw InvalidPasswordFormatException()
            }
        }.substring(8)
    }
}