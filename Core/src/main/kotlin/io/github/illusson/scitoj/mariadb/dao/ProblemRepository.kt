package io.github.illusson.scitoj.mariadb.dao

import io.github.illusson.scitoj.mariadb.domain.Problem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ProblemRepository: JpaRepository<Problem, Int> {
    @Query(
        "select top :size * from (" +
                "  select top (:page * :size) " +
                "    row_number() over (order by :order) as `RowNum`, " +
                "    * from `problems` where `p_show_public` = true and `p_show_guest` = :guest" +
                ") as tempTable " +
                "where `RowNum` between (:page - 1) * :size + 1 and :page * :size",
        nativeQuery = true
    )
    fun listForPublic(
        page: Int, size: Int = 20,
        guest: Boolean = false,
        order: String = "desc"
    ): ArrayList<Problem>

    @Query(
        "select top :size * from (" +
                "  select top (:page * :size) " +
                "    row_number() over (order by :order) as `RowNum`, " +
                "    * from `problems`" +
                ") as tempTable " +
                "where `RowNum` between (:page - 1) * :size + 1 and :page * :size",
        nativeQuery = true
    )
    fun listForAdmin(
        page: Int, size: Int = 20,
        order: String = "desc"
    ): ArrayList<Problem>
}