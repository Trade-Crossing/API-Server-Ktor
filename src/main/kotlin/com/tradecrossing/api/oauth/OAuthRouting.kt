@file:OptIn(ExperimentalSerializationApi::class)

package com.tradecrossing.api.oauth

import com.tradecrossing.dto.response.oauth.OAuthResponse
import com.tradecrossing.service.OAuthService
import com.tradecrossing.system.plugins.generateJwtToken
import com.tradecrossing.types.OAuthProvider
import io.github.smiley4.ktorswaggerui.dsl.get
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.ExperimentalSerializationApi
import org.koin.java.KoinJavaComponent.inject

fun Route.oauth() {
  val service by inject<OAuthService>(OAuthService::class.java)

  route("google") {
    authenticate("google") {
      get("/login") {}

      get("/callback", {}) {
        val principal: OAuthAccessTokenResponse.OAuth2? = call.principal()
        val accessToken = principal?.accessToken!!
        val result = service.getGoogleUserInfo(accessToken)
        val resident = service.findOrRegisterUser(result.email, result.id, OAuthProvider.google)
        val token = application.generateJwtToken(resident.id)
        val response = OAuthResponse(resident.registered, result.picture, token.accessToken, token.refreshToken)

        call.respond(HttpStatusCode.OK, response)
      }
    }
  }

  route("kakao") {
    authenticate("kakao") {
      get("/login") {}

      get("/callback") {
        val principal: OAuthAccessTokenResponse.OAuth2? = call.principal()
        val accessToken = principal?.accessToken!!
        val result = service.getKakaoUserInfo(accessToken)
        val resident = service.findOrRegisterUser(result.kakaoAccount.email, result.id.toString(), OAuthProvider.kakao)
        val token = application.generateJwtToken(resident.id)
        val response = OAuthResponse(
          resident.registered,
          result.kakaoAccount.profile.profileImageUrl,
          token.accessToken,
          token.refreshToken
        )
        call.respond(HttpStatusCode.OK, response)
      }
    }
  }
}