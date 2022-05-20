package io.github.illusson.scitoj.util

import io.github.sgpublic.aidescit.api.core.util.JwtUtil
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class JwtTest {
    @Autowired
    private lateinit var token: JwtUtil

    @Test
    fun doJwd() {
        val username = "username"
        val src = token.create(username, "password")
        println(src)
        val check = token.check(src)
        assert(username == check)
    }
}