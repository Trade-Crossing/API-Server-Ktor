package com.tradecrossing.dto.response.oauth

import kotlinx.serialization.Serializable

@Serializable
data class GoogleResponse(
  val id: String,
  val email: String,
  val verifiedEmail: Boolean,
  val name: String,
  val givenName: String,
  val familyName: String,
  val picture: String,
  val locale: String
)