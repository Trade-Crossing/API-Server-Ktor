package com.tradecrossing

import com.tradecrossing.plugins.*
import io.ktor.server.application.*

fun main(args: Array<String>) {
  io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
  configureSecurity()
  configureHTTP()
  configureSerialization()
  configureDatabases()
  configureRouting()
}
