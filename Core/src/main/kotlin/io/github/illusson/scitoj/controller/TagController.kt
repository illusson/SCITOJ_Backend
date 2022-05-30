package io.github.illusson.scitoj.controller

import io.github.illusson.scitoj.dto.request.TagListDto
import io.github.illusson.scitoj.dto.response.PagedDto
import io.github.illusson.scitoj.mariadb.domain.TagChart
import io.github.illusson.scitoj.module.ProblemTagModule
import io.github.sgpublic.aidescit.api.core.spring.annotation.ApiGetMapping
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springdoc.api.annotations.ParameterObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RestController

@Tag(name = "题目标签类")
@RestController
class TagController {
    @Autowired
    private lateinit var tags: ProblemTagModule

    @Operation(summary = "标签列表", description = "列出所有标签及其 ID。")
    @ApiGetMapping("/api/tags")
    fun getTags(@ParameterObject page: TagListDto): PagedDto<TagChart> {
        val list = tags.listTags(page.pageable)
        return PagedDto.of(list)
    }
}