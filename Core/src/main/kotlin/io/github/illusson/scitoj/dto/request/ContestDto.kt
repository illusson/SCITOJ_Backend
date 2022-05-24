package io.github.illusson.scitoj.dto.request

import io.github.sgpublic.aidescit.api.dto.SignedRequestDto
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.core.convert.converter.Converter
import org.springframework.web.bind.annotation.RequestParam
import java.time.Duration
import java.util.*
import javax.persistence.Convert

data class CreateContestDto(
    val title: String
): SignedRequestDto()

data class EditContestDto(
    @Schema(name = "id", required = true)
    @RequestParam(name = "id")
    var cid: Int,

    @Schema(name = "desc", required = true)
    @RequestParam(name = "desc")
    var description: String,

    @Schema(name = "start_time", required = true)
    @RequestParam(name = "start_time")
    var startTime: Date,

    @Schema(name = "duration", required = true)
    @RequestParam(name = "duration")
    @Convert(converter = Duration2LongConverter::class)
    var duration: Duration,

    @Schema(name = "show_guest")
    @RequestParam(name = "show_guest", required = false)
    var showGuest: Boolean? = null,

    @Schema(name = "show_public")
    @RequestParam(name = "show_public", required = false)
    var showPublic: Boolean? = null,

    @RequestParam(required = false)
    var title: String? = null,

    @RequestParam(required = false)
    var problem: List<Int>? = null
): SignedRequestDto() {
    class Duration2LongConverter: Converter<Long, Duration> {
        override fun convert(source: Long): Duration {
            return Duration.ofMinutes(source)
        }
    }
}