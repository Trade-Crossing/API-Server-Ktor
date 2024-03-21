package com.tradecrossing.api.oauth

import com.tradecrossing.dto.request.oauth.MobileLoginRequest
import com.tradecrossing.dto.response.ErrorResponse
import com.tradecrossing.dto.response.oauth.OAuthResponse
import com.tradecrossing.types.OAuthProvider
import io.github.smiley4.ktorswaggerui.dsl.OpenApiRoute
import io.ktor.http.*
import io.ktor.resources.*

@Resource("/oauth")
class OAuthResource {

  @Resource("/mobile")
  class Mobile(val parent: OAuthResource = OAuthResource()) {
    companion object {
      val post: OpenApiRoute.() -> Unit = {
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
    }
  }

  @Resource("/google")
  class Google(val parent: OAuthResource = OAuthResource()) {

    @Resource("/callback")
    class CallBack(val parent: Google = Google())

    companion object {
      val googleLogin: OpenApiRoute.() -> Unit = {
        tags = listOf("OAuth")
        summary = "Google OAuth2 실행하는 API"
      }
      val googleCallBack: OpenApiRoute.() -> Unit = {
        tags = listOf("OAuth")
        summary = "Google OAuth 콜백"
        response {
          HttpStatusCode.OK to {
            body<OAuthResponse>()
            description = "성공"
          }
        }
      }
    }
  }

  @Resource("/kakao")
  class Kakao(val parent: OAuthResource = OAuthResource()) {

    @Resource("/callback")
    class CallBack(val parent: Kakao = Kakao())

    companion object {
      val kakao: OpenApiRoute.() -> Unit = {
        tags = listOf("OAuth")
        summary = "Kakao OAuth 실행하는 API"
      }
      val kakaoCallBack: OpenApiRoute.() -> Unit = {
        tags = listOf("OAuth")
        summary = "Kakao OAuth 콜백"
        response {
          HttpStatusCode.OK to {
            body<OAuthResponse>()
            description = "성공"
          }
        }
      }
    }
  }
}

