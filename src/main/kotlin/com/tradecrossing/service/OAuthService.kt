package com.tradecrossing.service

import com.tradecrossing.dto.response.oauth.GoogleResponse
import com.tradecrossing.dto.response.oauth.KakaoResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy
import org.slf4j.LoggerFactory


class OAuthService {
  private val log = LoggerFactory.getLogger(OAuthService::class.java)

  @OptIn(ExperimentalSerializationApi::class)
  private val client = HttpClient(CIO) {
    install(ContentNegotiation) {
      json(Json {
        namingStrategy = JsonNamingStrategy.SnakeCase
        ignoreUnknownKeys = true
        encodeDefaults = true
      })
    }
  }

  suspend fun getGoogleUserInfo(accessToken: String) {
    val response = client.get("https://www.googleapis.com/oauth2/v2/userinfo") {
      bearerAuth(accessToken)
    }

    println(response.body<GoogleResponse>())
  }

  suspend fun getKakaoUserInfo(accessToken: String) {
    val response = client.get("https://kapi.kakao.com/v2/user/me") {
      bearerAuth(accessToken)
    }

    log.info(response.body<KakaoResponse>().toString())
  }
}