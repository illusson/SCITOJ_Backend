package io.github.sgpublic.aidescit.api.dto.response

import io.github.sgpublic.aidescit.api.dto.BaseResponseDto
import io.github.sgpublic.aidescit.api.mariadb.domain.Info

data class UserInfoDto(
    var info: Info
): BaseResponseDto()