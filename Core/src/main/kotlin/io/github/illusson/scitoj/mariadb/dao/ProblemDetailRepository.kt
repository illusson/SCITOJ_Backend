package io.github.illusson.scitoj.mariadb.dao

import io.github.illusson.scitoj.mariadb.domain.ProblemDetail
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProblemDetailRepository: JpaRepository<ProblemDetail, Int> {
}