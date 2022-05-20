package io.github.sgpublic.aidescit.api.core.spring.config

import io.github.sgpublic.aidescit.api.core.spring.property.SqlProperty
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn
import javax.sql.DataSource

/**
 * 自定义配置
 */
@Configuration
class CurrentConfig {
    @DependsOn("sqlProperty")
    @Bean(name = ["dataSource"])
    fun getDataSource(): DataSource {
        return DataSourceBuilder.create().apply {
            driverClassName(SqlProperty.DRIVER_CLASS_NAME)
            url(SqlProperty.URL)
            username(SqlProperty.USERNAME)
            password(SqlProperty.PASSWORD)
        }.build()
    }

    @DependsOn("sqlProperty")
    @Bean(name = ["databasePlatform"])
    fun getDatabasePlatform(): String {
        return SqlProperty.DATABASE_PLATFORM
    }
}