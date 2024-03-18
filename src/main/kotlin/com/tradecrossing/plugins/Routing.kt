package com.tradecrossing.plugins

import com.tradecrossing.api.oauth.oauth
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.openapi.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.resources.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
  install(Resources)
  install(CORS) {
    allowHost("localhost:8080")
  }
  routing {
    openAPI(path = "openapi")
    swaggerUI(path = "swagger-ui")

    // oauth routes
    route("oauth") {
      oauth()
    }
  }

}
