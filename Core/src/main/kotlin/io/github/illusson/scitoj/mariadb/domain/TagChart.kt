package io.github.illusson.scitoj.mariadb.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "problem_tag_chart")
class TagChart {
    @Id
    @Column(name = "t_id")
    val id: Int = 0

    @Column(name = "t_name")
    var name: String = ""
}