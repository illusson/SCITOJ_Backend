package io.github.sgpublic.aidescit.api.exceptions

import org.springframework.security.authentication.BadCredentialsException

/**
 * 密码错误，用户登录密码错误时抛出
 */
class WrongPasswordException(username: String): BadCredentialsException("<$username> 账号或密码错误")