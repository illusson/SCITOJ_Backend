package io.github.sgpublic.aidescit.api.controller

import io.github.sgpublic.aidescit.api.core.util.random
import io.github.sgpublic.aidescit.api.dto.response.PublicKeyDto
import io.github.sgpublic.aidescit.api.module.APIModule
import io.github.sgpublic.aidescit.api.module.PublicKeyModule
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "登录类", description = "用户登录")
@RestController
class PublicKeyController {
    @Autowired
    private lateinit var public: PublicKeyModule

    @Operation(summary = "公钥获取接口", description = "用于获取密码加密传输时的加密公钥，会同时返回密码加盐所需的字段。")
    @ApiResponse(responseCode = "200", description = "success.", content = [
        Content(mediaType = "application/json", schema = Schema(implementation = PublicKeyDto::class), examples = [
            ExampleObject(
                "{\n" +
                        "  \"code\": 200,\n" +
                        "  \"message\": \"success.\",\n" +
                        "  \"key\": \"MIGfMA0...AQAB\",\n" +
                        "  \"hash\": \"MIGfMA0...AQAB\",\n" +
                        "  \"ts\": \n" +
                        "}"
            )
        ])
    ])
    @GetMapping("/aidescit/public_key", consumes = ["application/x-www-form-urlencoded"])
    fun getKey(): PublicKeyDto {
        val ts = APIModule.TS
        return PublicKeyDto(
            key = public.getPublicKey(),
            hash = ts.random(),
            ts = ts
        )
    }
}