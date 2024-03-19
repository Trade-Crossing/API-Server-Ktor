package com.tradecrossing.api.oauth

import com.tradecrossing.service.OAuthService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

fun Route.oauth() {
  val service by inject<OAuthService>(OAuthService::class.java)

  route("google") {
    authenticate("google") {
      get("/login") {}

      get("/callback") {
        val principal: OAuthAccessTokenResponse.OAuth2? = call.principal()
        val accessToken = principal?.accessToken!!

        service.getGoogleUserInfo(accessToken)
        println(principal)
      }
    }
  }

  route("kakao") {
    authenticate("kakao") {
      get("/login") {}

      get("/callback") {
        val principal: OAuthAccessTokenResponse.OAuth2? = call.principal()
        val accessToken = principal?.accessToken!!

        service.getKakaoUserInfo(accessToken)
        println(principal)
        call.respond(HttpStatusCode.OK)
      }
    }
  }


}