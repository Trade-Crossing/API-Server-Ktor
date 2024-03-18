package com.tradecrossing.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*

fun Application.configureStatusPages() {

  install(StatusPages) {

  }
}