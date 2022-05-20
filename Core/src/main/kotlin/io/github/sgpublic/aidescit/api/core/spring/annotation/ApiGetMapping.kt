package io.github.sgpublic.aidescit.api.core.spring.annotation

import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Encoding
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.core.annotation.AliasFor
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@Target(AnnotationTarget.FUNCTION)
@RequestMapping(method = [RequestMethod.GET], consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
@RequestBody(content = [Content(encoding = [Encoding(
    contentType = MediaType.APPLICATION_FORM_URLENCODED_VALUE
)])])
@ApiResponse(content = [Content(encoding = [Encoding(
    contentType = MediaType.APPLICATION_JSON_VALUE
)])], responseCode = "200")
annotation class ApiGetMapping(
    @get:AliasFor(attribute = "name", annotation = RequestMapping::class) val path: String
)
