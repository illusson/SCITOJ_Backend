package io.github.illusson.scitoj.mariadb.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import io.github.illusson.scitoj.dto.response.ProblemDetailDto
import io.github.sgpublic.aidescit.api.core.util.JsonDateDeserializer
import io.github.sgpublic.aidescit.api.core.util.JsonDateSerializer
import io.swagger.v3.oas.annotations.media.Schema
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "problems")
open class Problem: SampleProblem, SimpleItem {
    @Column(name = "p_id")
    override val id: Int = 0

    @Id
    @Column(name = "p_display_id")
    override var displayId: String = ""

    @Column(name = "p_title")
    override var title: String = ""

    @Schema(name = "create_time")
    @Column(name = "p_create_time")
    override var createTime: Date = Date()

    @Schema(name = "create_user")
    @Column(name = "p_create_user")
    override var createUser: String = ""

    @Schema(name = "edit_user")
    @Column(name = "p_edit_user")
    override var editUser: String? = null

    @Schema(name = "edit_time")
    @Column(name = "p_edit_time")
    override var editTime: Date? = null

    @Schema(name = "show_guest")
    @Column(name = "p_show_guest")
    override var showGuest: Boolean = false

    @Schema(name = "show_public")
    @Column(name = "p_show_public")
    override var showPublic: Boolean = false

    @Column(name = "p_daily")
    override var daily: Date? = null
}

interface SampleProblem: IdItem {
    var title: String

    @get:Schema(implementation = Long::class)
    @get:JsonSerialize(using = JsonDateSerializer::class)
    @get:JsonDeserialize(using = JsonDateDeserializer::class)
    var daily: Date?
}

interface ProblemStatus {
    var status: ProblemDetailDto.StateParam
    var passingRate: Int
}

class SampleStatusProblem(
    override val id: Int,
    @Schema(name = "display_id")
    override var displayId: String,
    override var title: String,
    override var daily: Date?,
    override var status: ProblemDetailDto.StateParam =
        ProblemDetailDto.StateParam.pending,
    @Schema(name = "passing_rate")
    override var passingRate: Int = 0,
) : SampleProblem, ProblemStatus {
    companion object {
        fun of(
            problem: SampleProblem, status: ProblemDetailDto.StateParam, passingRate: Int
        ): SampleStatusProblem {
            return SampleStatusProblem(
                problem.id, problem.displayId, problem.title,
                problem.daily, status, passingRate
            )
        }
    }
}

class StatusProblem(
    override val id: Int,

    @Schema(name = "display_id")
    override var displayId: String,

    override var title: String,

    @Schema(name = "create_time")
    override var createTime: Date,

    @Schema(name = "create_user")
    override var createUser: String,

    @Schema(name = "edit_user")
    override var editUser: String?,

    @Schema(name = "edit_time")
    override var editTime: Date?,

    @JsonIgnore
    @Schema(name = "show_guest", hidden = true)
    override var showGuest: Boolean,

    @JsonIgnore
    @Schema(name = "show_public", hidden = true)
    override var showPublic: Boolean,

    override var status: ProblemDetailDto.StateParam =
        ProblemDetailDto.StateParam.pending,

    @Schema(name = "passing_rate")
    override var passingRate: Int = 0,
): Problem(), ProblemStatus {

    companion object {
        fun of(
            problem: Problem, status: ProblemDetailDto.StateParam, passingRate: Int
        ): StatusProblem {
            return StatusProblem(
                problem.id ,problem.displayId, problem.title,
                problem.createTime, problem.createUser,
                problem.editUser, problem.editTime,
                problem.showGuest, problem.showPublic,
                status, passingRate
            )
        }
    }
}