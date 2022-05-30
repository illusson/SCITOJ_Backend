package io.github.sgpublic.aidescit.api.core.util

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

class JsonDateSerializer: JsonSerializer<Date?>() {
    override fun serialize(value: Date?, gen: JsonGenerator, serializers: SerializerProvider) {
        if (value == null) {
            gen.writeNull()
        } else {
            gen.writeNumber(value.time / 1000)
        }
    }
}

class JsonDateDeserializer: JsonDeserializer<Date?>() {
    override fun deserialize(p: JsonParser?, ctxt: DeserializationContext?): Date? {
        val time = p?.text?.toLongOrNull() ?: return null
        return Date(time * 1000)
    }
}

@Target(AnnotationTarget.FIELD, AnnotationTarget.FUNCTION, AnnotationTarget.TYPE, AnnotationTarget.PROPERTY_GETTER)
@Retention(AnnotationRetention.RUNTIME)
@Schema(implementation = Long::class)
@JsonSerialize(using = JsonDateSerializer::class)
@JsonDeserialize(using = JsonDateDeserializer::class)
annotation class JsonDateConverter
