package io.github.sgpublic.aidescit.api.core.spring.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenDocConfig {
    @Bean
    fun springShopOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info().title("SCITOJ API")
                    .description("适用于四川工业科技学院在线判题平台的开放 API")
//                    .version("v0.0.1")
//                    .license(License().name("Apache 2.0").url("http://springdoc.org"))
            )
//            .externalDocs(
//                ExternalDocumentation()
//                    .description("SpringShop Wiki Documentation")
//                    .url("https://springshop.wiki.github.org/docs")
//            )
    }
}