package com.tradecrossing

import com.tradecrossing.plugins.configureRouting
import com.tradecrossing.plugins.configureSecurity
import com.tradecrossing.plugins.configureSerialization
import com.tradecrossing.plugins.configureStatusPages
import io.ktor.server.application.*

fun main(args: Array<String>) = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
  configureSecurity()
  configureSerialization()
  configureRouting()
  configureStatusPages()
}
