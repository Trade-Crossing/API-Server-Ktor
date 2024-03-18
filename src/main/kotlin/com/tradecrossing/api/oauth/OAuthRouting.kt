package com.tradecrossing.api.oauth

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*

fun Route.oauth() {

  route("google") {
    authenticate("google") {
      get("/login") {}

      get("/callback") {
        val principal: OAuthAccessTokenResponse.OAuth2? = call.principal()

        println(principal)
      }
    }
  }

  route("kakao") {
    authenticate("kakao") {
      get("/login") {}

      get("/callback") {
        val principal: OAuthAccessTokenResponse.OAuth2? = call.principal()
        println(principal)
      }
    }
  }


}