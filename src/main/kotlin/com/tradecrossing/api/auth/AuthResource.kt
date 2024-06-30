package com.tradecrossing.api.auth

import com.tradecrossing.domain.ResidentInfoDto
import com.tradecrossing.dto.request.auth.IslandCodeRequest
import com.tradecrossing.dto.request.auth.RegisterRequest
import com.tradecrossing.dto.response.ErrorResponse
import io.github.smiley4.ktorswaggerui.dsl.OpenApiRoute
import io.ktor.http.*
import io.ktor.resources.*


@Resource("/auth")
class AuthResource {

  @Resource("/info")
  class Info(val parent: AuthResource = AuthResource()) {

    @Resource("/island-code")
    class IslandCode(val parent: Info = Info()) {
      companion object {
        val get: OpenApiRoute.() -> Unit = {
          tags = listOf("인증/유저")
          summary = "섬 코드"
          description = "유저의 섬 코드를 가져옵니다."
          securitySchemeName = "Jwt"
          protected = true
          response {
            HttpStatusCode.OK to {
              body<String>()
              description = "성공"
            }

            HttpStatusCode.NotFound to {
              body<ErrorResponse>()
              description = "존재하지 않는 유저입니다."
            }

            HttpStatusCode.Unauthorized to {
              body<ErrorResponse>()
              description = "인증에 실패했습니다."
            }
          }
        }

        val update: OpenApiRoute.() -> Unit = {
          tags = listOf("인증/유저")
          summary = "섬 코드 수정"
          description = "유저의 섬 코드를 수정합니다."
          securitySchemeName = "Jwt"
          protected = true
          request {
            body<IslandCodeRequest>()
          }
          response {
            HttpStatusCode.OK to {
              description = "성공"
            }

            HttpStatusCode.NotFound to {
              body<ErrorResponse>()
              description = "존재하지 않는 유저입니다."
            }

            HttpStatusCode.Unauthorized to {
              body<ErrorResponse>()
              description = "인증에 실패했습니다."
            }
          }
        }
      }
    }

    companion object {
      val get: OpenApiRoute.() -> Unit = {
        tags = listOf("인증/유저")
        summary = "회원정보"
        description = "회원정보를 가져옵니다."
        securitySchemeName = "Jwt"
        request {
          headerParameter<String>("Authorization") {
            description = "Bearer Token"
            required = true
          }
        }
        response {
          HttpStatusCode.OK to {
            body<ResidentInfoDto>()
            description = "성공"
          }

          HttpStatusCode.NotFound to {
            body<ErrorResponse>()
            description = "존재하지 않는 유저입니다."
          }

          HttpStatusCode.Unauthorized to {
            body<ErrorResponse>()
            description = "인증에 실패했습니다."
          }
        }
      }
    }
  }

  @Resource("/register")
  class Register(val parent: AuthResource = AuthResource()) {
    companion object {
      val post: OpenApiRoute.() -> Unit = {
        tags = listOf("인증/유저")
        summary = "회원가입"
        description = "회원가입을 합니다."
        request {
          body<RegisterRequest>()
        }

        response {
          // 409
          HttpStatusCode.Conflict to {
            description = "이미 가입된 회원입니다."
            body<ErrorResponse> {
              example("conflict", ErrorResponse("conflict", "이미 가입된 회원입니다."))
            }
          }

          // 404
          HttpStatusCode.NotFound to {
            description = "존재하지 않는 "
            body<ErrorResponse> {
              example("not_found", ErrorResponse("not_found", "회원가입에 실패했습니다."))
            }
          }

        }
      }
    }
  }

  @Resource("/refresh")
  class Refresh(val parent: AuthResource = AuthResource())
}