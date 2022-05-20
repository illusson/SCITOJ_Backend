package io.github.sgpublic.aidescit.api.dto.response

import io.github.sgpublic.aidescit.api.dto.BaseResponseDto

data class UserInfoDto(
    var info: Info
): BaseResponseDto() {
    data class Info(
        var name: String,
        var nickname: String?,
        var identify: Description<Int>,
        var role: Description<String>,
        var faculty: String?,
        var specialty: String?,
        var `class`: String?,
        var grade: String?,
    )

    data class Description<T>(
        var id: T,
        var desc: String
    )
}