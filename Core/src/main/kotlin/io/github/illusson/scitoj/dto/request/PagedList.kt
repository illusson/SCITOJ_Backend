package io.github.illusson.scitoj.dto.request

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import javax.persistence.Transient

interface PagedList {
    val page: Int

    val size: Int get() = 20

    @get:Transient
    val pageable: Pageable get() = PageRequest.of(page, size)
}

interface SortedList<T: Enum<T>>: PagedList {
    val order: OrderParam?

    val basis: T?

    @get:Transient
    override val pageable: Pageable get() = if (basis != null && order != null) {
        PageRequest.of(page, size, Sort.by(order!!.getDirection(), basis!!.name))
    } else { super.pageable }

    enum class OrderParam {
        desc, asc;

        fun getDirection(): Sort.Direction {
            return when(this) {
                asc -> Sort.Direction.ASC
                desc -> Sort.Direction.DESC
            }
        }
    }
}

