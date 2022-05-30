package io.github.illusson.scitoj.mariadb.dao

import io.github.illusson.scitoj.mariadb.domain.Problem
import io.github.sgpublic.aidescit.api.core.util.and
import io.github.sgpublic.aidescit.api.core.util.removeAll
import io.github.sgpublic.aidescit.api.core.util.retainAll
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository
import java.util.*
import javax.persistence.criteria.Predicate

@Repository
interface ProblemRepository: JpaRepository<Problem, String>, JpaSpecificationExecutor<Problem>

fun ProblemRepository.listPublic(
    pageable: Pageable, authed: Boolean,
    include: HashSet<String>? = null,
    tagFilter: HashSet<String>? = null,
    exclude: HashSet<String>? = null,
    daily: Boolean? = null
): Page<Problem> {
    return findAll(find@{ root, query, builder ->
        var tmp: Predicate = builder.equal(root.get<Boolean>("p_show_guest"), authed)

        tmp = builder.and(tmp, builder.equal(root.get<Boolean>("p_show_public"), true))

        limits(include, tagFilter, exclude)?.let {
            tmp = builder.and(tmp, root.get<String>("p_id").`in`(it))
        }
        if (daily != null) {
            tmp = builder.and(tmp, if (daily) {
                root.get<Date>("p_daily").isNotNull
            } else {
                root.get<Date>("p_daily").isNull
            })
        }

        return@find tmp
    }, pageable)
}

fun ProblemRepository.listAdmin(
    pageable: Pageable,
    include: HashSet<String>? = null,
    tagFilter: HashSet<String>? = null,
    exclude: HashSet<String>? = null,
    daily: Boolean? = null
): Page<Problem> {
    return findAll(find@{ root, query, builder ->
        var tmp: Predicate? = null

        limits(include, tagFilter, exclude)?.let {
            tmp = tmp.and(builder, root.get<String>("p_id").`in`(it))
        }
        if (daily != null) {
            tmp = builder.and(tmp, if (daily) {
                root.get<Date>("p_daily").isNotNull
            } else {
                root.get<Date>("p_daily").isNull
            })
        }

        return@find tmp
    }, pageable)
}

private fun limits(
    state: HashSet<String>? = null,
    pid: HashSet<String>? = null,
    exclude: HashSet<String>? = null
): Set<String>? {
    var limit: HashSet<String>? = null
    if (pid != null) {
        limit = limit.retainAll(pid)
    }
    if (state != null) {
        limit = limit.retainAll(state)
    }
    if (exclude != null) {
        limit = limit.removeAll(exclude)
    }
    return limit
}