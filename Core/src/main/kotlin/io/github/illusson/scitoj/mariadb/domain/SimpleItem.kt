package io.github.illusson.scitoj.mariadb.domain

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import io.github.sgpublic.aidescit.api.core.util.JsonDateDeserializer
import io.github.sgpublic.aidescit.api.core.util.JsonDateSerializer
import io.swagger.v3.oas.annotations.media.Schema
import java.io.Serializable
import java.util.*

interface SimpleItem: Serializable {
    @get:Schema(implementation = Long::class)
    @get:JsonSerialize(using = JsonDateSerializer::class)
    @get:JsonDeserialize(using = JsonDateDeserializer::class)
    var createTime: Date

    var createUser: String

    var editUser: String?

    @get:Schema(implementation = Long::class)
    @get:JsonSerialize(using = JsonDateSerializer::class)
    @get:JsonDeserialize(using = JsonDateDeserializer::class)
    var editTime: Date?

    var showGuest: Boolean

    var showPublic: Boolean
}

interface IdItem: Serializable {
    val id: Int

    var displayId: String
}