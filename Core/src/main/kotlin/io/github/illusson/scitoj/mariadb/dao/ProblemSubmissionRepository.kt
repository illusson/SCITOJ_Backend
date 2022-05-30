package io.github.illusson.scitoj.mariadb.dao

import io.github.illusson.scitoj.mariadb.domain.ProblemSubmission
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ProblemSubmissionRepository: JpaRepository<ProblemSubmission, Int> {
    @Query(
        "select * from `problem_submission` where `p_id` = :pid",
        nativeQuery = true
    )
    fun listByPid(pid: String): List<ProblemSubmission>?

    @Query(
        "select * from `problem_submission` where `u_id` = :uid",
        nativeQuery = true
    )
    fun listByUid(uid: String): List<ProblemSubmission>?

    @Query(
        "select * from `problem_submission` where `u_id` = :uid and `p_id` = :pid",
        nativeQuery = true
    )
    fun listByUidAndPid(uid: String, pid: String): List<ProblemSubmission>?
}