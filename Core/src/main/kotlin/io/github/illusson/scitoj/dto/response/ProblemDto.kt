package io.github.illusson.scitoj.dto.response

import io.github.illusson.scitoj.mariadb.domain.Problem
import io.github.sgpublic.aidescit.api.dto.BaseResponseDto
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "题目列表响应数据")
data class ProblemListDto(
    @Schema(description = "题目列表")
    val list: ArrayList<Problem>
): BaseResponseDto()

data class ProblemDetailDto(
    val data: Problem? = null
): BaseResponseDto()

data class ProblemCreateDto(
    val id: Int
): BaseResponseDto()