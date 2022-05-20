package io.github.illusson.scitoj.mariadb.domain

import io.github.sgpublic.aidescit.api.core.util.fromGson
import io.github.sgpublic.aidescit.api.core.util.toGson
import java.io.Serializable
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "problems")
class Problem: Serializable {
    @Id
    @Column(name = "p_id")
    val id: Int = 0

    @Column(name = "p_display_id")
    var displayId: String? = null

    @Column(name = "p_title")
    var title: String = ""

    @Column(name = "p_description")
    var description: String? = null

    @Column(name = "p_sample")
    @Convert(converter = SampleConverter::class)
    var sample: ArrayList<Sample> = ArrayList()

    @Column(name = "p_hint")
    var hint: String = ""

//    @Transient
//    @Column(name = "p_solution")
//    var solution: String = ""

    @Column(name = "p_create_time")
    var createTime: Date = Date()

    @Column(name = "p_create_user")
    var createUser: String = ""

    @Column(name = "p_edit_user")
    var editUser: String? = null

    @Column(name = "p_edit_time")
    var editTime: Date? = null

    @Column(name = "p_show_guest")
    var showGuest: Boolean = false

    @Column(name = "p_show_public")
    var showPublic: Boolean = false

    @Column(name = "p_daily")
    var daily: Date? = null

    data class Sample(
        val input: String,
        val output: String,
        val desc: String? = null
    )
    class SampleList: ArrayList<Sample>()
    @Converter
    class SampleConverter: AttributeConverter<ArrayList<Sample>, String> {
        override fun convertToDatabaseColumn(attribute: ArrayList<Sample>): String {
            return attribute.toGson()
        }

        override fun convertToEntityAttribute(dbData: String?): ArrayList<Sample> {
            return SampleList::class.fromGson(
                dbData ?: return arrayListOf()
            )
        }
    }
}