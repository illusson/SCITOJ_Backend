package io.github.illusson.scitoj.mariadb.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import io.swagger.v3.oas.annotations.media.Schema
import java.time.Duration
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "contests")
class Contest: SampleContest, SimpleItem {
    @Column(name = "con_id")
    override val id: Int = 0

    @Id
    @Column(name = "con_display_id")
    override var displayId: String = ""

    @Column(name = "con_title")
    override var title: String = ""

    @Column(name = "con_description")
    override var description: String? = null

    @Column(name = "con_start_time")
    var startTime: Date? = null

    @Column(name = "con_duration")
    @Convert(converter = DurationConverter::class)
    var duration: Duration? = null

    @Schema(name = "create_time")
    @Column(name = "con_create_time")
    override var createTime: Date = Date()

    @Schema(name = "create_user")
    @Column(name = "con_create_user")
    override var createUser: String = ""

    @Schema(name = "edit_user")
    @Column(name = "con_edit_user")
    override var editUser: String? = null

    @Schema(name = "edit_time")
    @Column(name = "con_edit_time")
    override var editTime: Date? = null

    @JsonIgnore
    @Schema(name = "show_guest", hidden = true)
    @Column(name = "con_show_guest")
    override var showGuest: Boolean = false

    @JsonIgnore
    @Schema(name = "show_public", hidden = true)
    @Column(name = "con_show_public")
    override var showPublic: Boolean = false

    @Converter
    class DurationConverter: AttributeConverter<Duration?, Long?> {
        override fun convertToDatabaseColumn(attribute: Duration?): Long? {
            return attribute?.toMinutes()
        }

        override fun convertToEntityAttribute(dbData: Long?): Duration? {
            return Duration.ofMinutes(dbData ?: return null)
        }
    }
}

interface SampleContest: IdItem {
    var title: String
    var description: String?
}