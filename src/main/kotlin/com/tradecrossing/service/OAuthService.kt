package com.tradecrossing.service

import com.tradecrossing.domain.entity.ResidentEntity
import com.tradecrossing.domain.tables.ResidentTable
import com.tradecrossing.dto.response.oauth.GoogleResponse
import com.tradecrossing.dto.response.oauth.KakaoResponse
import com.tradecrossing.dto.response.resident.ResidentDto
import com.tradecrossing.system.plugins.DatabaseFactory.dbQuery
import com.tradecrossing.types.OAuthProvider
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy
import org.jetbrains.exposed.sql.and
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

  suspend fun getGoogleUserInfo(accessToken: String): GoogleResponse {
    val response = client.get("https://www.googleapis.com/oauth2/v2/userinfo") {
      bearerAuth(accessToken)
    }

    return response.body<GoogleResponse>()
  }

  suspend fun getKakaoUserInfo(accessToken: String): KakaoResponse {
    val response = client.get("https://kapi.kakao.com/v2/user/me") {
      bearerAuth(accessToken)
    }

    return response.body<KakaoResponse>()
  }

  suspend fun findOrRegisterUser(email: String, providerId: String, provider: OAuthProvider): ResidentDto {
    return dbQuery {
      var resident = ResidentEntity.find {
        (ResidentTable.email eq email) and
            (ResidentTable.providerId eq providerId) and
            (ResidentTable.provider eq provider)
      }.firstOrNull()

      if (resident == null) {
        resident = ResidentEntity.new {
          this.email = email
          this.provider = provider
          this.providerId = providerId
        }
      }

      ResidentDto(resident)
    }
  }
}
