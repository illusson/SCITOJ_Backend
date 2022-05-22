package io.github.illusson.scitoj.mariadb.dao

import io.github.illusson.scitoj.mariadb.domain.Problem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ProblemRepository: JpaRepository<Problem, Int> {
    @Query(
        "select * from `problems` " +
                "where `p_show_public` = true and `p_show_guest` = :guest " +
                "order by :order limit :start, :offset",
        nativeQuery = true
    )
    fun listForPublic(
        start: Int, offset: Int = 20,
        guest: Boolean = false,
        order: String = "desc"
    ): List<Problem>

    @Query(
        "select * from `problems` order by :order limit :start, :offset",
        nativeQuery = true
    )
    fun listForAdmin(
        start: Int, offset: Int = 20,
        order: String = "desc"
    ): List<Problem>

    @Query(
        "select * from `problems` " +
                "where `p_id` in :pidList and `p_show_public` = true and `p_show_guest` = :guest " +
                "order by :order limit :start, :offset",
        nativeQuery = true
    )
    fun listPublicProblemIn(
        pidList: List<Int>,
        start: Int, offset: Int = 20,
        guest: Boolean = false,
        order: String = "desc"
    ): List<Problem>

    @Query(
        "select * from `problems` " +
                "where `p_id` in :pidList " +
                "order by :order limit :start, :offset",
        nativeQuery = true
    )
    fun listAdminProblemIn(
        pidList: List<Int>,
        start: Int, offset: Int = 20,
        order: String = "desc"
    ): List<Problem>
}