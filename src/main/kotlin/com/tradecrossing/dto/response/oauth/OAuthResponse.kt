package com.tradecrossing.dto.response.oauth

import kotlinx.serialization.Serializable

@Serializable
data class OAuthResponse(
  val registered: Boolean,
  val profilePic: String,
  val accessToken: String,
  val refreshToken: String,
)
