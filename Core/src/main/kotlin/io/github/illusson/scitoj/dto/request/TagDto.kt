package io.github.illusson.scitoj.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.web.bind.annotation.RequestParam

data class TagProblemDto(
    @Schema(name = "t_id", required = true)
    @RequestParam(name = "t_id")
    val tid: List<Int>
): ListPageDto()