package com.tradecrossing.dto.request.oauth

import com.tradecrossing.types.OAuthProvider
import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Serializable

@Serializable
@Schema(requiredProperties = ["provider_id", "email", "provider"])
data class MobileLoginRequest(
  @field:Schema(name = "provider_id", description = "OAuth 제공자 ID", example = "1234567890")
  val providerId: String,
  @field:Schema(name = "email", description = "이메일", example = "email@email.com")
  val email: String,
  @field:Schema(
    name = "provider",
    description = "OAuth 제공자",
    example = "google",
    examples = ["google", "kakao", "apple"]
  )
  val provider: OAuthProvider
)