package com.tradecrossing.system.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.request.*
import org.slf4j.event.Level

fun Application.configureCallLogging() {
  install(CallLogging) {
    level = Level.INFO
    format { call ->
      val statusCode = call.response.status()
      val method = call.request.httpMethod.value
      val url = call.request.uri
      val query = call.request.queryParameters.entries().joinToString("&") { (key, values) ->
        values.joinToString("&") { "$key=$it" }
      }
      val processingTime = call.processingTimeMillis()

      "$statusCode [$method] - $url in $processingTime ms \n"
    }
  }
}