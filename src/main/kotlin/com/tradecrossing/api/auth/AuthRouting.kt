package com.tradecrossing.api.auth


import com.tradecrossing.dto.request.auth.RegisterRequest
import com.tradecrossing.service.AuthService
import com.tradecrossing.system.plugins.getUserId
import com.tradecrossing.system.plugins.withAuth
import com.tradecrossing.types.TokenType
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
    post<AuthResource.Register>(AuthResource.Register.post) {
      val body = call.receive<RegisterRequest>()
      val id = call.getUserId()

      authService.registerUser(id, body)
      call.respond(HttpStatusCode.Created, "성공")
    }

    get<AuthResource.Info>(AuthResource.Info.get) {
      val id = call.getUserId()
      val response = authService.getResidentInfo(id)

      call.respond(response)
    }


  }

  withAuth(TokenType.REFRESH) {
    post<AuthResource.Refresh> {}
  }
}