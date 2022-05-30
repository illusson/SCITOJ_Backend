package io.github.illusson.scitoj.mariadb.dao

import io.github.illusson.scitoj.mariadb.domain.ContestProblem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ContestProblemRepository: JpaRepository<ContestProblem, Int> {
    @Query(
        "select `p_id` from `contest_problem` where `con_id` = :id",
        nativeQuery = true
    )
    fun getByContestId(id: String): List<String>?

    @Query(
        "select `con_id` from `contest_problem` where `p_id` = :id",
        nativeQuery = true
    )
    fun getByProblemId(id: String): List<String>?

    @Query(
        "delete from `contest_problem` where `con_id` = :id",
        nativeQuery = true
    )
    fun deleteByContestId(id: String)
}