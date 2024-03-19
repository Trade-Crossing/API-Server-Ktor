package com.tradecrossing.dto.response.oauth

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class KakaoResponse(
  val id: Long,
  val connectedAt: Instant,
  val properties: Properties,
  val kakaoAccount: KakaoAccount,
)

@Serializable
data class Properties(
  val nickname: String,
  val profileImage: String,
  val thumbnailImage: String
)

@Serializable
data class KakaoAccount(
  val profileNicknameNeedsAgreement: Boolean,
  val profileImageNeedsAgreement: Boolean,
  val profile: KakaoProfile,
  val hasEmail: Boolean,
  val emailNeedsAgreement: Boolean,
  val isEmailValid: Boolean,
  val isEmailVerified: Boolean,
  val email: String
)

@Serializable
data class KakaoProfile(
  val nickname: String,
  val thumbnailImageUrl: String,
  val profileImageUrl: String,
  val isDefaultImage: Boolean,
  val isDefaultNickname: Boolean
)