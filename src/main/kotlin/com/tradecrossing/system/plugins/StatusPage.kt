package com.tradecrossing.system.plugins

import com.tradecrossing.dto.response.ErrorResponse
import com.tradecrossing.system.exceptions.ConfictException
import com.tradecrossing.system.exceptions.UnauthorizedException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureStatusPages() {
  install(StatusPages) {

    // 401 Unauthorized
    exception<UnauthorizedException> { call, e ->
      call.respond(HttpStatusCode.Unauthorized, ErrorResponse("unauthorized", e.message ?: "Unauthorized"))
    }

    exception<NotFoundException> { call, e ->
      call.respond(HttpStatusCode.NotFound, ErrorResponse("not_found", e.message ?: "Not Found"))
    }

    exception<ConfictException> { call, e ->
      call.respond(HttpStatusCode.Conflict, ErrorResponse("conflict", e.message ?: "Conflict"))
    }

  }
}