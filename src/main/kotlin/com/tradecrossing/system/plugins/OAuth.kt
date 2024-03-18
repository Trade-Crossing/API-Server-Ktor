package com.tradecrossing.system.plugins

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*


private val httpClient = HttpClient(CIO) {
  install(ContentNegotiation) {
    json()
  }
}

fun Application.configureOAuth() {

  install(Authentication) {
    oauth("google") {
      urlProvider = { "http://localhost:8080/api/oauth/google/callback" }
      providerLookup = {
        OAuthServerSettings.OAuth2ServerSettings(
          name = "google",
          authorizeUrl = "https://accounts.google.com/o/oauth2/auth",
          accessTokenUrl = "https://oauth2.googleapis.com/token",
          requestMethod = HttpMethod.Post,
          clientId = System.getenv("GOOGLE_CLIENT_ID"),
          clientSecret = System.getenv("GOOGLE_SECRET_KEY"),
          defaultScopes = listOf(
            "https://www.googleapis.com/auth/userinfo.profile",
            "https://www.googleapis.com/auth/userinfo.email"
          ),
          extraAuthParameters = listOf(
            "access_type" to "offline",
          ),
          accessTokenRequiresBasicAuth = false
        )
      }
      client = httpClient
    }

    oauth("kakao") {
      urlProvider = { "http://localhost:8080/api/oauth/kakao/callback" }
      providerLookup = {
        OAuthServerSettings.OAuth2ServerSettings(
          name = "kakao",
          authorizeUrl = "https://kauth.kakao.com/oauth/authorize",
          accessTokenUrl = "https://kauth.kakao.com/oauth/token",
          requestMethod = HttpMethod.Post,
          clientId = System.getenv("KAKAO_REST_API_KEY"),
          clientSecret = System.getenv("KAKAO_SECRET_KEY"),
          defaultScopes = emptyList(),
          accessTokenRequiresBasicAuth = false
        )
      }
      client = httpClient
    }
  }
}
