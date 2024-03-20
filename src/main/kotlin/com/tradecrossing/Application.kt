package com.tradecrossing

import com.tradecrossing.system.plugins.*
import io.ktor.server.application.*

fun main(args: Array<String>) = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
  DatabaseFactory.init(environment.config)
  configureKoin()
  configureOAuth()
  configureSerialization()
  //configureSwaggerUI()
  configureStatusPages()
  configureRouting()
}
