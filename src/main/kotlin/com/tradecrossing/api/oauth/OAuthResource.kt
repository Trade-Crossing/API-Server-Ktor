package com.tradecrossing.api.oauth

import io.ktor.resources.*

@Resource("/oauth")
class OAuthResource {
  @Resource("/google")
  class Google(val parent: OAuthResource = OAuthResource()) {

    @Resource("/callback")
    class CallBack(val parent: Google = Google())
  }

  @Resource("/kakao")
  class Kakao(val parent: OAuthResource = OAuthResource()) {

    @Resource("/callback")
    class CallBack(val parent: Kakao = Kakao())
  }
}

