package io.github.sgpublic.aidescit.api.core.util

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.github.sgpublic.aidescit.api.exceptions.GsonException
import io.github.sgpublic.aidescit.api.module.CONTENT_TYPE
import io.github.sgpublic.aidescit.api.module.JSON_UTF_8
import okhttp3.Headers
import okhttp3.Response
import okhttp3.internal.closeQuietly
import javax.persistence.Transient
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.reflect.KClass

public val GSON: Gson = GsonBuilder().disableHtmlEscaping().create()

@Transient
fun <T: Any> KClass<T>.fromGson(src: String): T {
    return GSON.fromJson(src, this.java)
        ?: throw GsonException()
}
@Transient
fun Any?.toGson(): String {
    return GSON.toJson(this)
}

fun <T: Any> Response.jsonBody(clazz: KClass<T>): T {
    return clazz.fromGson(textBody())
}

fun Response.textBody(): String {
    val body = this.body?.string().toString()
    this.body?.closeQuietly()
    return body
}

fun <T: Any> HttpServletResponse.writeJson(src: T) {
    setHeader(Headers.CONTENT_TYPE, Headers.JSON_UTF_8)
    writer.let {
        it.write(src.toGson())
        it.flush()
        it.closeQuietly()
    }
}

fun <T: Any> HttpServletRequest.readJson(clazz: KClass<T>): T {
    return clazz.fromGson(reader.readText())
}