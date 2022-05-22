package io.github.illusson.scitoj.module

import io.github.illusson.scitoj.dto.request.ListPageDto
import io.github.illusson.scitoj.mariadb.dao.TagChartRepository
import io.github.illusson.scitoj.mariadb.domain.TagChart
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ProblemTagModule {
    @Autowired
    private lateinit var tagChart: TagChartRepository


    fun listTags(page: ListPageDto): List<TagChart> {
        return tagChart.list(page.start, page.size, page.order)
    }
}