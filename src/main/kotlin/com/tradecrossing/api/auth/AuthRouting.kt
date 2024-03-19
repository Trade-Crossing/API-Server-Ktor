package com.tradecrossing.api.auth


import com.tradecrossing.dto.request.auth.RegisterRequest
import com.tradecrossing.dto.response.ErrorResponse
import com.tradecrossing.dto.response.resident.ResidentInfoDto
import com.tradecrossing.service.AuthService
import com.tradecrossing.system.plugins.getUserId
import com.tradecrossing.system.plugins.withAuth
import com.tradecrossing.types.TokenType
import io.github.smiley4.ktorswaggerui.dsl.OpenApiRoute
import io.github.smiley4.ktorswaggerui.dsl.resources.get
import io.github.smiley4.ktorswaggerui.dsl.resources.post
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

fun Route.auth() {
  val authService by inject<AuthService>(AuthService::class.java)

  withAuth(TokenType.ACCESS) {
    post<AuthResource.Register>(register) {
      val body = call.receive<RegisterRequest>()
      val id = call.getUserId()

      authService.registerUser(id, body)
      call.respond(HttpStatusCode.Created, "성공")
    }

    get<AuthResource.Info>(info) {
      val id = call.getUserId()
      val response = authService.getResidentInfo(id)

      call.respond(response)
    }
  }

  withAuth(TokenType.REFRESH) {
    post<AuthResource.Refresh> {}
  }
}


private val register: OpenApiRoute.() -> Unit = {
  tags = listOf("Auth")
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

private val info: OpenApiRoute.() -> Unit = {
  tags = listOf("Auth")
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