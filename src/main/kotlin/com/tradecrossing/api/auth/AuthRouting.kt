package com.tradecrossing.api.auth

import com.tradecrossing.dto.request.auth.RegisterRequest
import com.tradecrossing.dto.response.ErrorResponse
import com.tradecrossing.service.AuthService
import com.tradecrossing.system.plugins.getUserId
import com.tradecrossing.system.plugins.withAuth
import com.tradecrossing.types.TokenType
import io.github.smiley4.ktorswaggerui.dsl.OpenApiRoute
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*

import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.Route


import org.koin.java.KoinJavaComponent.inject

fun Route.auth() {
  val authService by inject<AuthService>(AuthService::class.java)

  withAuth(TokenType.ACCESS) {
    post<AuthResource.Register> {
      val body = call.receive<RegisterRequest>()
      val id = call.getUserId()

      authService.registerUser(id, body)
      call.respond(HttpStatusCode.Created, "성공")
    }
  }

  withAuth(TokenType.REFRESH) {
    post<AuthResource.Refresh> {}
  }
}


private val register: OpenApiRoute.() -> Unit = {
  tags = listOf("auth")
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