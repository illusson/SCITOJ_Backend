package io.github.illusson.scitoj.dto.response

import io.github.illusson.scitoj.mariadb.domain.TagChart

data class TagListDto(
    val list: List<TagChart>
)