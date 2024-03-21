package com.tradecrossing.api.oauth

import com.tradecrossing.dto.request.oauth.MobileLoginRequest
import com.tradecrossing.dto.response.oauth.OAuthResponse
import com.tradecrossing.service.OAuthService
import com.tradecrossing.system.plugins.generateJwtToken
import com.tradecrossing.types.OAuthProvider
import io.github.smiley4.ktorswaggerui.dsl.resources.get
import io.github.smiley4.ktorswaggerui.dsl.resources.post
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

fun Route.oauth() {
  val service by inject<OAuthService>(OAuthService::class.java)

  authenticate("google") {
    // Google OAuth2 실행하는 API
    get<OAuthResource.Google>(OAuthResource.Google.googleLogin) {}

    get<OAuthResource.Google.CallBack>(OAuthResource.Google.googleCallBack) {
      val principal: OAuthAccessTokenResponse.OAuth2? = call.principal()
      val accessToken = principal?.accessToken!!
      val result = service.getGoogleUserInfo(accessToken)
      val resident = service.findOrRegisterUser(result.email, result.id, OAuthProvider.google)
      val token = application.generateJwtToken(resident.id)
      val response = OAuthResponse(resident.registered, result.picture, token.accessToken, token.refreshToken)

      call.respond(HttpStatusCode.OK, response)
    }
  }

  authenticate("kakao") {
    get<OAuthResource.Kakao>(OAuthResource.Kakao.kakao) {}

    get<OAuthResource.Kakao.CallBack>(OAuthResource.Kakao.kakaoCallBack) {
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

  post<OAuthResource.Mobile>(OAuthResource.Mobile.post) {
    val body = call.receive<MobileLoginRequest>()
    val resident = service.mobileLogin(body)
    val token = application.generateJwtToken(resident.id)
    val response = OAuthResponse(resident.registered, null, token.accessToken, token.refreshToken)

    call.respond(HttpStatusCode.OK, response)
  }
}