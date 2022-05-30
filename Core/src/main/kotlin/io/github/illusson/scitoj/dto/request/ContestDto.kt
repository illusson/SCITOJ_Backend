package io.github.illusson.scitoj.dto.request

import io.github.sgpublic.aidescit.api.dto.SignedRequest
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.core.convert.converter.Converter
import org.springframework.web.bind.annotation.RequestParam
import java.time.Duration
import java.util.*
import javax.persistence.Convert

data class CreateContest(
    @Schema(description = "竞赛 ID", required = true)
    val id: String,
    @Schema(description = "竞赛标题", required = true)
    val title: String,

    override val ts: Long,
    override val sign: String
): SignedRequest

data class EditContest(
    @Schema(name = "id", required = true)
    @RequestParam(name = "id")
    val cid: String,

    @Schema(name = "desc", required = true)
    @RequestParam(name = "desc")
    val description: String,

    @Schema(name = "start_time", required = true, implementation = Long::class)
    @RequestParam(name = "start_time")
    val startTime: Date,

    @Schema(name = "duration", required = true, implementation = Long::class)
    @RequestParam(name = "duration")
    @Convert(converter = Duration2LongConverter::class)
    val duration: Duration,

    @Schema(name = "show_guest")
    @RequestParam(name = "show_guest", required = false)
    val showGuest: Boolean? = null,

    @Schema(name = "show_public")
    @RequestParam(name = "show_public", required = false)
    val showPublic: Boolean? = null,

    @RequestParam(required = false)
    val title: String? = null,

    @RequestParam(required = false)
    val problem: List<Int>? = null,

    override val ts: Long,
    override val sign: String
): SignedRequest {
    class Duration2LongConverter: Converter<Long, Duration> {
        override fun convert(source: Long): Duration {
            return Duration.ofMinutes(source)
        }
    }
}