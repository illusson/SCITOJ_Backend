package io.github.sgpublic.aidescit.api.core.util

import java.io.Serializable
import java.math.BigInteger
import java.security.MessageDigest

private val instance: MessageDigest get() = MessageDigest.getInstance("MD5")

/**
 * 16 位 MD5
 */
val String.MD5: String get() {
    return MD5_FULL.substring(5, 24)
}

/**
 * 32 位 MD5
 */
val String.MD5_FULL: String get() {
    val digest = instance.digest(toByteArray())
    return StringBuffer().run {
        for (b in digest) {
            val i :Int = b.toInt() and 0xff
            var hexString = Integer.toHexString(i)
            if (hexString.length < 2) {
                hexString = "0$hexString"
            }
            append(hexString)
        }
        toString()
    }
}

/**
 * 8 位 MD5，由 16 位 MD5 转换为 32 进制得来
 */
val String.MD5_COMPRESSED: String get() {
    return BigInteger(MD5, 16).toString(32)
}

/**
 * 8 位 MD5，由 16 位 MD5 转换为 32 进制得来
 */
val String.MD5_FULL_COMPRESSED: String get() {
    return BigInteger(MD5_FULL, 16).toString(32)
}

/**
 * 16 位 MD5
 */
val Serializable.MD5: String get() {
    return MD5_FULL.substring(5, 24)
}

/**
 * 32 位 MD5
 */
val Serializable.MD5_FULL: String get() {
    return toGson().MD5_FULL
}

/**
 * 8 位 MD5，由 16 位 MD5 转换为 32 进制得来
 */
val Serializable.MD5_COMPRESSED: String get() {
    return BigInteger(MD5, 16).toString(32)
}

/**
 * 8 位 MD5，由 16 位 MD5 转换为 32 进制得来
 */
val Serializable.MD5_FULL_COMPRESSED: String get() {
    return BigInteger(MD5_FULL, 16).toString(32)
}
