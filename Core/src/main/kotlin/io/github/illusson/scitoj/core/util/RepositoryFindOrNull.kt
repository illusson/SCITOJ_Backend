package io.github.illusson.scitoj.core.util

import io.github.illusson.scitoj.mariadb.domain.SimpleItem
import io.github.sgpublic.aidescit.api.core.base.CurrentUser
import io.github.sgpublic.aidescit.api.exceptions.ProblemNotFoundException
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull

fun <T: SimpleItem, ID> JpaRepository<T, ID>.findByIdAuthed(id: ID): T {
    return findByIdOrNull(id)?.takeIf {
        if (!CurrentUser.IS_AUTHENTICATED) {
            return@takeIf it.showPublic && it.showGuest
        }
        if (CurrentUser.USER_INFO.IS_ADMIN) {
            return@takeIf true
        }
        return@takeIf it.showPublic
    } ?: throw ProblemNotFoundException()
}