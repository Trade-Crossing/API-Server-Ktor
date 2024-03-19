@file:OptIn(ExperimentalSerializationApi::class)

package com.tradecrossing.system.plugins

import io.github.smiley4.ktorswaggerui.SwaggerUI
import io.github.smiley4.ktorswaggerui.data.AuthKeyLocation
import io.github.smiley4.ktorswaggerui.data.AuthScheme
import io.github.smiley4.ktorswaggerui.data.AuthType
import io.ktor.server.application.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy
import kotlinx.serialization.serializer


fun Application.configureSwaggerUI() {
  install(SwaggerUI) {
    info {
      title = "TradeCrossing API"
      version = "1.0.0"
      description = "TradeCrossing 프로젝트의 API"
    }

    swagger {
      swaggerUrl = "swagger-ui"
    }

    encoding {
      exampleEncoder { type, example ->
        val json = Json {
          prettyPrint = true
          encodeDefaults = true
          namingStrategy = JsonNamingStrategy.SnakeCase
        }

        json.encodeToString(serializer(type!!), example)
      }
    }

    // 로컬 서버
    server {
      url = "http://localhost:8080/api"
      description = "로컬 서버"
    }

    // 개발 서버
    server {
      url = "https://xxx.com"
      description = "개발 서버"
    }

    securityScheme("Jwt") {
      type = AuthType.HTTP
      location = AuthKeyLocation.HEADER
      bearerFormat = "jwt"
      scheme = AuthScheme.BEARER
      description = "JWT 인증 토큰"
    }


    // OAuth 태그
    tag("OAuth") { description = "OAuth 인증 관련 API" }

    // Auth 태그
    tag("Auth") { description = "인증 관련 API" }
  }
}