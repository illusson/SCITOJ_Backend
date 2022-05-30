package io.github.illusson.scitoj.mariadb.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import io.github.sgpublic.aidescit.api.core.util.fromGson
import io.github.sgpublic.aidescit.api.core.util.toGson
import io.swagger.v3.oas.annotations.media.Schema
import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "problem_detail")
class ProblemDetail: Serializable {
    @Id
    @Column(name = "p_id")
    @JsonIgnore
    @Schema(hidden = true)
    var id: String = ""

    @Column(name = "p_description")
    var description: String = ""

    @Column(name = "p_sample")
    @Convert(converter = SampleConverter::class)
    var sample: List<Sample>? = null

    @Column(name = "p_hint")
    var hint: String? = null

    data class Sample(
        val input: String,
        val output: String,
        val desc: String? = null
    )
    class SampleList: ArrayList<Sample>()
    @Converter
    class SampleConverter: AttributeConverter<List<Sample>?, String?> {
        override fun convertToDatabaseColumn(attribute: List<Sample>?): String? {
            return (attribute ?: return null).toGson()
        }

        override fun convertToEntityAttribute(dbData: String?): List<Sample>? {
            return SampleList::class.fromGson(
                dbData ?: return null
            )
        }
    }
}