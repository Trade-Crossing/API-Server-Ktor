package com.tradecrossing.system.plugins

import io.github.smiley4.ktorswaggerui.SwaggerUI
import io.github.smiley4.ktorswaggerui.data.AuthKeyLocation
import io.github.smiley4.ktorswaggerui.data.AuthScheme
import io.github.smiley4.ktorswaggerui.data.AuthType
import io.ktor.server.application.*


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

    securityScheme("Jwt Token") {
      type = AuthType.HTTP
      location = AuthKeyLocation.HEADER
      bearerFormat = "jwt"
      scheme = AuthScheme.BEARER
      description = "JWT 인증 토큰"
    }

    tag("OAuth") {
      description = "OAuth 인증 관련 API"
    }
  }
}