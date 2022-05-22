package io.github.illusson.scitoj.dto.response

import io.github.illusson.scitoj.mariadb.domain.ProblemDetail
import io.github.illusson.scitoj.mariadb.domain.SampleProblem
import io.github.illusson.scitoj.mariadb.domain.TagChart
import io.github.sgpublic.aidescit.api.dto.BaseResponseDto
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "题目列表响应数据")
data class ProblemListDto<T: SampleProblem>(
    @Schema(description = "题目列表")
    val list: List<T>
): BaseResponseDto()

data class ProblemDetailDto<T: SampleProblem>(
    val info: T? = null,
    val detail: ProblemDetail? = null,
    val tags: List<TagChart> = listOf()
): BaseResponseDto()

data class ProblemCreateDto(
    val id: Int
): BaseResponseDto()