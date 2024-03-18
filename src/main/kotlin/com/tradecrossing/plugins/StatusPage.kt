package com.tradecrossing.plugins

import com.tradecrossing.exceptions.UnauthorizedException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureStatusPages() {
  install(StatusPages) {

    // 401 Unauthorized
    exception<UnauthorizedException> { call, e ->
      call.respond(HttpStatusCode.Unauthorized, e.message ?: "Unauthorized")
    }

  }
}