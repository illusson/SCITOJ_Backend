package io.github.illusson.scitoj.mariadb.domain

import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "problem_submission")
class ProblemSubmission {
    @Id
    @Column(name = "sub_id")
    val id: Long = 0

    @Column(name = "u_id")
    var username: String = ""

    @Column(name = "p_id")
    var pid: String = ""

    @Column(name = "sub_time")
    var subTime: Date = Date()

    @Column(name = "sub_code")
    var subCode: String = ""

    @Column(name = "sub_code_type")
    lateinit var subCodeType: CodeType

    @Column(name = "sub_status")
    var subStatus: SubStatus = SubStatus.pending

    enum class CodeType(id: String) {
        java("Java"), python("Python"), c("C"), cpp("C++"),
    }

    enum class SubStatus(id: String) {
        pending("Pending"), compiling("Compiling"), judging("Judging"),

        compile_error("Compile Error"),

        accepted("Accepted"),

        wrong_answer("Wrong Answer"), runtime_error("Runtime Error"),
        time_limit("Time Limit Exceeded"), memory_limit("Memory Limit Exceeded"),

        unkonwn_error("Unknown Error")
    }
}