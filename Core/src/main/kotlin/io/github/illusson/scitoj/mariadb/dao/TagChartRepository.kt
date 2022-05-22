package io.github.illusson.scitoj.mariadb.dao

import io.github.illusson.scitoj.mariadb.domain.TagChart
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface TagChartRepository: JpaRepository<TagChart, Int> {
    @Query(
        "select `t_id` from `problem_tag_chart` where `t_name` = :name",
        nativeQuery = true
    )
    fun findByName(name: String): Int?

    @Query(
        "select * from `problem_tag_chart` where `t_id` in :ids",
        nativeQuery = true
    )
    fun getTagIn(ids: List<Int>): List<TagChart>

    @Query(
        "select * from `problem_tag_chart` order by :order limit :start, :size",
        nativeQuery = true
    )
    fun list(start: Int, size: Int, order: String): List<TagChart>
}