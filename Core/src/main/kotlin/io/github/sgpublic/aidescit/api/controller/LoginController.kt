package io.github.sgpublic.aidescit.api.controller

import io.github.sgpublic.aidescit.api.core.spring.annotation.ApiGetMapping
import io.github.sgpublic.aidescit.api.core.spring.annotation.ApiPostMapping
import io.github.sgpublic.aidescit.api.core.util.random
import io.github.sgpublic.aidescit.api.dto.request.LoginDto
import io.github.sgpublic.aidescit.api.dto.response.AccessTokenDto
import io.github.sgpublic.aidescit.api.dto.response.PublicKeyDto
import io.github.sgpublic.aidescit.api.module.APIModule
import io.github.sgpublic.aidescit.api.module.PublicKeyModule
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RestController
import javax.servlet.ServletException

@Tag(name = "登录类", description = "用户登录")
@RestController
class LoginController {
    @Autowired
    private lateinit var public: PublicKeyModule

    @Operation(summary = "公钥获取接口", description = "用于获取密码加密传输时的加密公钥，会同时返回密码加盐所需的字段。")
    @ApiGetMapping("/aidescit/public_key")
    fun getPublicKey(): PublicKeyDto {
        val ts = APIModule.TS
        return PublicKeyDto(
            key = public.getPublicKey(),
            hash = ts.random(),
            ticket = ts
        )
    }

    @Operation(summary = "登录接口", description = "提交账号密码登录，密码需提交加盐密文密码")
    @ApiPostMapping("/aidescit/login")
    fun login(login: LoginDto): AccessTokenDto {
        throw ServletException("use authentication provider instead.")
    }
}