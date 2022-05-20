package io.github.illusson.scitoj.core

import io.github.sgpublic.aidescit.api.core.util.toGson
import io.github.sgpublic.aidescit.api.module.JSON_UTF_8
import okhttp3.Headers
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okio.BufferedSink

class JsonBody private constructor(
    private val body: Map<String, Any?>
): RequestBody() {
    override fun contentType(): MediaType {
        return Headers.JSON_UTF_8.toMediaType()
    }

    override fun writeTo(sink: BufferedSink) {
        sink.write(body.toGson().toByteArray())
    }

    class Builder: LinkedHashMap<String, Any?>() {
        fun build(): JsonBody {
            return JsonBody(this)
        }
    }
}

public fun jsonBodyOf(vararg pairs: Pair<String, Any?>): JsonBody {
    return JsonBody.Builder().also {
        for ((key, value) in pairs) {
            it[key] = value
        }
    }.build()
}