package com.tradecrossing.system.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*

fun Application.configureRequestValidation() {
  install(RequestValidation) {}
}