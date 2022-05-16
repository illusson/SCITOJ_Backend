package io.github.sgpublic.aidescit.api.core.util

import io.github.Application
import io.github.sgpublic.aidescit.api.core.spring.property.TokenProperty
import io.github.sgpublic.aidescit.api.module.APIModule
import io.github.sgpublic.aidescit.api.module.SessionModule
import io.jsonwebtoken.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.io.Serializable
import java.security.Key
import java.util.*

/**
 * token 管理，支持生成和检查与刷新 token
 */
@Component
class JwtUtil {
    @Autowired
    private lateinit var session: SessionModule

    fun create(username: String, password: String,
               createTime: Long = APIModule.TS_FULL): String {
        val map = mutableMapOf<String, Any>()
        map["username"] = username
        map["tag"] = JwtBody.create(password)
        return Jwts.builder()
            .setClaims(map)
            .setSubject(Application.PROJECT_NAME)
            .signWith(TokenProperty.TOKEN_SIGN_KEY)
            .setExpiration(Date(createTime + TokenProperty.ACCESS_EXPIRED * 1000))
            .setNotBefore(Date(createTime))
            .compact()
    }

    fun check(token: String): String {
        val parse = Jwts.parserBuilder()
            .requireSubject(Application.PROJECT_NAME)
            .setSigningKeyResolver(object : SigningKeyResolver {
                private val key = TokenProperty.TOKEN_SIGN_KEY
                override fun resolveSigningKey(header: JwsHeader<*>, claims: Claims): Key = key
                override fun resolveSigningKey(header: JwsHeader<*>, plaintext: String): Key = key
            })
            .build().parse(token)
        val claim = parse.body as Claims
        val username = claim["username"] as String?
            ?: throw MalformedJwtException("unknown username")
        val password = RSAUtil.decodePassword(
            session.get(username).password
        )
        if (claim["tag"] as String? != JwtBody.create(password)) {
            throw MalformedJwtException("unknown tag")
        }
        return username
    }

    private data class JwtBody(
        val password: String,
        val secret: String = TokenProperty.TOKEN_KEY
    ): Serializable {
        companion object {
            fun create(password: String): String {
                return JwtBody(password).MD5_FULL_COMPRESSED
            }
        }
    }
}