package io.github.illusson.scitoj.dto.request

import io.github.sgpublic.aidescit.api.dto.SignedRequest

data class TagListDto(
    override val page: Int,
    override val size: Int,
    override val ts: Long,
    override val sign: String
): PagedList, SignedRequest
