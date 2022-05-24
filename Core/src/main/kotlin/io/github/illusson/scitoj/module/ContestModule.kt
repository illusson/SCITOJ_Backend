package io.github.illusson.scitoj.module

import io.github.illusson.scitoj.core.util.findByIdAuthed
import io.github.illusson.scitoj.exceptions.ItemEditException
import io.github.illusson.scitoj.mariadb.dao.ContestProblemRepository
import io.github.illusson.scitoj.mariadb.dao.ContestRepository
import io.github.illusson.scitoj.mariadb.domain.Contest
import io.github.illusson.scitoj.mariadb.domain.ContestProblem
import io.github.sgpublic.aidescit.api.core.base.CurrentUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.Duration
import java.util.*

@Component
class ContestModule {
    @Autowired
    private lateinit var contest: ContestRepository
    @Autowired
    private lateinit var conProblem: ContestProblemRepository
    @Autowired
    private lateinit var problem: ProblemModule

    fun create(title: String): Int {
        Contest().also {
            it.createTime = Date()
            it.createUser = CurrentUser.USERNAME
            it.title = title
        }.let {
            return contest.save(it).id
        }
    }

    fun edit(
        cid: Int, description: String, start: Date,
        duration: Duration, showGuest: Boolean? = null,
        showPublic: Boolean? = null, title: String? = null,
        problem: List<Int>? = null
    ) {
        val con = contest.findByIdAuthed(cid)
        title?.let { con.title = title }
        con.description = description
        con.startTime = start
        con.duration = duration

        con.showGuest = showGuest == true
        con.showPublic = con.showGuest || showPublic == true

        val pList = conProblem.getByContestId(cid)
        if (problem != null) {
            conProblem.deleteAllById(pList ?: listOf())
            conProblem.saveAll(problem.map { pid ->
                return@map ContestProblem().also {
                    it.pid = pid
                    it.cid = cid
                }
            })
        } else if (con.showPublic && pList.isNullOrEmpty()) {
            throw ItemEditException("请至少设置一题后再公开比赛！")
        }

        contest.save(con)
    }
}