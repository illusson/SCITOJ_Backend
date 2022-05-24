package io.github.illusson.scitoj.mariadb.domain

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "contest_problem")
class ContestProblem: Serializable {
    @Id
    @Column(name = "id")
    val id: Int = 0

    @Column(name = "con_id")
    var cid: Int = 0

    @Column(name = "p_id")
    var pid: Int = 0
}