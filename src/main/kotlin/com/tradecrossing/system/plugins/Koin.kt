package com.tradecrossing.system.plugins

import com.tradecrossing.repository.ChatRepository
import com.tradecrossing.repository.ReportRepository
import com.tradecrossing.repository.ResidentRepository
import com.tradecrossing.service.AuthService
import com.tradecrossing.service.ChatService
import com.tradecrossing.service.OAuthService
import com.tradecrossing.service.TradeService
import io.ktor.server.application.*
import org.koin.core.logger.Level.DEBUG
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin() {
  install(Koin) {
    slf4jLogger(level = DEBUG)
    val repositories = module {
      singleOf(::ResidentRepository)
      singleOf(::ChatRepository)
      singleOf(::ReportRepository)
    }

    val services = module {
      singleOf(::OAuthService)
      singleOf(::AuthService)
      singleOf(::TradeService)
      singleOf(::ChatService)
    }


    modules(repositories, services)
  }
}