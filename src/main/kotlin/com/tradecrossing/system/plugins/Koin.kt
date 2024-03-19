package com.tradecrossing.system.plugins

import com.tradecrossing.service.OAuthService
import io.ktor.server.application.*
import org.koin.core.logger.Level.DEBUG
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin() {
  install(Koin) {
    slf4jLogger(level = DEBUG)
    val services = module {
      single { OAuthService() }
    }
    
    modules(services)
  }
}