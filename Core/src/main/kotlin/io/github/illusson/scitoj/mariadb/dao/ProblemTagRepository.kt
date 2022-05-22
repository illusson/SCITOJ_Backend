package io.github.illusson.scitoj.mariadb.dao

import io.github.illusson.scitoj.mariadb.domain.ProblemTags
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ProblemTagRepository: JpaRepository<ProblemTags, Long> {
    @Query(
        "select `t_id` from `problem_tags` where `p_id` = :pid",
        nativeQuery = true
    )
    fun getTagsByProblem(pid: Int): List<Int>

    @Query(
        "select `p_id` from `problem_tags` where `t_id` in :tid",
        nativeQuery = true
    )
    fun getProblemByTags(tid: List<Int>): List<Int>
}