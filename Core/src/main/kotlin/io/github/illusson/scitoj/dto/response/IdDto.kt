package io.github.illusson.scitoj.dto.response

import io.github.sgpublic.aidescit.api.dto.BaseResponseDto

class IdDto<T>(
    val id: T
): BaseResponseDto()