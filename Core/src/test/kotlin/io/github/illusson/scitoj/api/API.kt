package io.github.illusson.scitoj.api

import io.github.illusson.scitoj.core.jsonBodyOf
import io.github.sgpublic.aidescit.api.core.util.RSAUtil
import io.github.sgpublic.aidescit.api.core.util.jsonBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.springframework.boot.test.context.SpringBootTest
import java.util.concurrent.TimeUnit

@SpringBootTest
abstract class API {
    protected val host = "http://192.168.31.29:18080"

    companion object {
//        private val springStarter: Object = Object()
//        private val spring: Thread = Thread {
//            synchronized(springStarter) {
//                springStarter.notify()
//            }
//        }

        private val client = OkHttpClient.Builder().apply {
            readTimeout(10, TimeUnit.SECONDS)
            writeTimeout(10, TimeUnit.SECONDS)
            connectTimeout(10, TimeUnit.SECONDS)
            callTimeout(10, TimeUnit.SECONDS)
            followRedirects(false)
            followSslRedirects(false)
        }.build()
        fun call(request: Request): Response {
            return client.newCall(request).execute()
        }
    }

//    @BeforeAll
//    fun startSpringBoot() {
//        spring.start()
//        synchronized(springStarter) {
//            springStarter.wait()
//        }
//        println("SpringBoot 启动完成")
//    }

    fun login(username: String, password: String): String {
        val pwd = RSAUtil.encode("12345678$password", RSAUtil.PUBLIC_CI)
        val body = jsonBodyOf(
            "username" to username,
            "password" to pwd
        )
        return call(
            Request.Builder()
                .url("$host/aidescit/login")
                .post(body)
                .build()
        ).jsonBody(object {
            val access_token: String = ""
        }::class).access_token
    }
}