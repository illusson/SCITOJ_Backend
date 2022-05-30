package io.github.illusson.scitoj.module

import io.github.illusson.scitoj.mariadb.dao.TagChartRepository
import io.github.illusson.scitoj.mariadb.domain.TagChart
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

@Component
class ProblemTagModule {
    @Autowired
    private lateinit var tagChart: TagChartRepository

    fun listTags(pageable: Pageable): Page<TagChart> {
        return tagChart.list(pageable)
    }
}