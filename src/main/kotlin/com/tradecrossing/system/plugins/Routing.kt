package com.tradecrossing.system.plugins

import com.tradecrossing.api.auth.auth
import com.tradecrossing.api.oauth.oauth
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.resources.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
  install(Resources)
  install(CORS) {
    allowHost("localhost:8080")
  }
  routing {
    //openAPI(path = "openapi")
    //swaggerUI(path = "swagger-ui")
    // oauth routes
    route("oauth") {
      oauth()
    }

    // auth routes
    auth()

    // user routes

    // trade routes

  }

}
