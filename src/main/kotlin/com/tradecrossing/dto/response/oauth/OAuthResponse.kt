package com.tradecrossing.dto.response.oauth

import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Serializable

@Serializable
@Schema
data class OAuthResponse(
  @field:Schema(description = "유저 등록 여부")
  val registered: Boolean,
  @field:Schema(name = "profile_pic", description = "프로필 사진")
  val profilePic: String,
  @field:Schema(name = "access_token", description = "액세스 토큰")
  val accessToken: String,
  @field:Schema(name = "refresh_token", description = "리프레시 토큰")
  val refreshToken: String,
)
