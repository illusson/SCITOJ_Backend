package io.github.illusson.scitoj.mariadb.domain

import java.util.*

interface SimpleItem {
    var createTime: Date

    var createUser: String

    var editUser: String?

    var editTime: Date?

    var showGuest: Boolean

    var showPublic: Boolean
}