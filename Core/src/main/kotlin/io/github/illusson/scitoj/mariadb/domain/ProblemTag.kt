package io.github.illusson.scitoj.mariadb.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "problem_tags")
class ProblemTags {
    @Id
    @Column(name = "id")
    val id: Long = 0

    @Column(name = "p_id")
    var pid: String = ""

    @Column(name = "t_id")
    var tid: Int = 0
}