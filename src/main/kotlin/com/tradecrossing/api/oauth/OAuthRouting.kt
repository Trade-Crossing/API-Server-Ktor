package com.tradecrossing.api.oauth

import com.tradecrossing.dto.request.oauth.MobileLoginRequest
import com.tradecrossing.dto.response.ErrorResponse
import com.tradecrossing.dto.response.oauth.OAuthResponse
import com.tradecrossing.service.OAuthService
import com.tradecrossing.system.plugins.generateJwtToken
import com.tradecrossing.types.OAuthProvider
import io.github.smiley4.ktorswaggerui.dsl.OpenApiRoute
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
    get<OAuthResource.Google>(google) {}

    get<OAuthResource.Google.CallBack>(googleCallBack) {
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
    get<OAuthResource.Kakao>(kakao) {}

    get<OAuthResource.Kakao.CallBack>(kakaoCallBack) {
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

  post<OAuthResource.Mobile>(mobile) {
    val body = call.receive<MobileLoginRequest>()
    val resident = service.mobileLogin(body)
    val token = application.generateJwtToken(resident.id)
    val response = OAuthResponse(resident.registered, null, token.accessToken, token.refreshToken)

    call.respond(HttpStatusCode.OK, response)
  }
}


private val google: OpenApiRoute.() -> Unit = {
  tags = listOf("OAuth")
  summary = "Google OAuth2 실행하는 API"
}

private val googleCallBack: OpenApiRoute.() -> Unit = {
  tags = listOf("OAuth")
  summary = "Google OAuth 콜백"
  response {
    HttpStatusCode.OK to {
      body<OAuthResponse>()
      description = "성공"
    }
  }
}

private val kakao: OpenApiRoute.() -> Unit = {
  tags = listOf("OAuth")
  summary = "Kakao OAuth 실행하는 API"
}

private val kakaoCallBack: OpenApiRoute.() -> Unit = {
  tags = listOf("OAuth")
  summary = "Kakao OAuth 콜백"
  response {
    HttpStatusCode.OK to {
      body<OAuthResponse>()
      description = "성공"
    }
  }
}

private val mobile: OpenApiRoute.() -> Unit = {
  tags = listOf("OAuth")
  summary = "모바일 로그인"
  request {
    body<MobileLoginRequest> {
      example("1", MobileLoginRequest("12345", "email@email.com", provider = OAuthProvider.google))
      example("2", MobileLoginRequest("12345", "email@email.com", provider = OAuthProvider.kakao))
    }
  }
  response {
    HttpStatusCode.OK to {
      body<OAuthResponse>()
      description = "성공"
    }

    HttpStatusCode.NotFound to {
      body<ErrorResponse>()
      description = "등록되지 않은 사용자입니다."
    }
  }
}