package io.github

import io.github.sgpublic.aidescit.api.core.spring.config.CurrentConfig
import io.github.sgpublic.aidescit.api.core.spring.config.OpenDocConfig
import io.github.sgpublic.aidescit.api.core.spring.config.SecurityConfig
import io.github.sgpublic.aidescit.api.core.util.ArgumentReader
import io.github.sgpublic.aidescit.api.core.util.Log
import io.github.sgpublic.aidescit.api.exceptions.ServerRuntimeException
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Import

/**
 * @author sgpublic
 * @date 2022/5/2 13:36
 */
@SpringBootApplication
@Import(CurrentConfig::class, SecurityConfig::class, OpenDocConfig::class)
class Application {
    companion object {
        const val PRO_PORT = "8088"
        const val DEV_PORT = "18088"
        const val PROJECT_NAME = "scitoj"

        /** 启动入口 */
        @JvmStatic
        fun main(args: Array<String>) {
            context = runApplication<Application>(*setup(args))
            Log.w("服务启动成功！")
            val listen = "127.0.0.1:${System.getProperty("server.port")}"
            Log.w("监听：$listen")
        }

        /** 是否为 Debug 环境 */
        private var debug = false
        val DEBUG: Boolean get() = debug

        /** ApplicationContext */
        private lateinit var context: ApplicationContext
        val CONTEXT: ApplicationContext get() = context

        /** 获取 Bean */
        inline fun <reified T> getBean(bean: String): T {
            val beanObject = CONTEXT.getBean(bean)
            if (beanObject !is T) {
                throw ServerRuntimeException.INTERNAL_ERROR
            }
            return beanObject
        }

        /** 初始化 SpringApplication 参数 */
        class ServletInitializer : SpringBootServletInitializer() {
            override fun configure(application: SpringApplicationBuilder): SpringApplicationBuilder {
                return application.apply {
                    lazyInitialization(true)
                    sources(Application::class.java)
                }
            }
        }

        /** 初始化参数 */
        private fun setup(args: Array<String>): Array<String> {
            val argsCurrent = arrayListOf(*args)
            val reader = ArgumentReader(args)
            debug = reader.containsItem("--debug")

            if (reader.getString("--spring.profiles.active", null) == null) {
                val arg = StringBuilder("--spring.profiles.active=")
                if (debug) arg.append("dev") else arg.append("pro")
                argsCurrent.add(arg.toString())
            }

            System.setProperty("server.port", if (debug) DEV_PORT else PRO_PORT)

            return Array(argsCurrent.size) {
                return@Array argsCurrent[it]
            }
        }
    }
}
