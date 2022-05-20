package io.github.sgpublic.aidescit.api.core.spring

import io.github.sgpublic.aidescit.api.core.util.ArgumentReader
import io.github.sgpublic.aidescit.api.core.util.fromGson
import org.springframework.boot.configurationprocessor.json.JSONObject
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.InputStreamReader
import java.nio.charset.Charset
import javax.servlet.ReadListener
import javax.servlet.ServletInputStream
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletRequestWrapper
import kotlin.reflect.KClass

class RepeatableHttpServletRequest(request: HttpServletRequest): HttpServletRequestWrapper(request) {
    private val bytes: ByteArray

    init {
        bytes = request.inputStream.readBytes()
    }

    override fun getInputStream(): ServletInputStream {
        return RepeatableInputSteam(bytes)
    }

    private class RepeatableInputSteam(bytes: ByteArray): ServletInputStream() {
        private val reader = ByteArrayInputStream(bytes)
        private var listener: ReadListener? = null

        override fun read(): Int {
            return reader.read()
        }

        override fun isFinished(): Boolean {
            val finished = reader.available() <= 0
            if (finished) listener?.onAllDataRead()
            return finished
        }

        override fun isReady(): Boolean {
            return true
        }

        override fun setReadListener(listener: ReadListener?) {
            this.listener = listener?.also {
                it.onDataAvailable()
            }
        }
    }

    override fun getReader(): BufferedReader {
        val bais = ByteArrayInputStream(bytes)
        return BufferedReader(InputStreamReader(bais))
    }

    fun textBody(charset: Charset = Charsets.UTF_8): String {
        return bytes.toString(charset)
    }

    private var formBody: Map<String, String>? = null
    fun formBody(charset: Charset = Charsets.UTF_8): Map<String, String> {
        formBody?.let { return it }
        val pairs = textBody(charset).split("&")
        val body = mutableMapOf<String, String>().also {
            for (str: String in pairs) {
                val pair = str.split("=")
                it[pair[0]] = pair[1]
            }
        }
        formBody = ArgumentReader.readRequestMap(body)
        return formBody!!
    }

    fun <T: Any> jsonBody(clazz: KClass<T>, charset: Charset = Charsets.UTF_8): T {
        return clazz.fromGson(textBody(charset))
    }

    fun jsonBody(charset: Charset = Charsets.UTF_8): JSONObject {
        return JSONObject(textBody(charset))
    }
}