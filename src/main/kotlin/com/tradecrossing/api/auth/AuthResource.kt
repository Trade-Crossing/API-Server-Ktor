package com.tradecrossing.api.auth

import io.ktor.resources.*


@Resource("/auth")
class AuthResource {

  @Resource("/register")
  class Register(val parent: AuthResource = AuthResource())

  @Resource("/refresh")
  class Refresh(val parent: AuthResource = AuthResource())
}