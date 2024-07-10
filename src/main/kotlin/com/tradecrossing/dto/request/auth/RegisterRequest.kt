package com.tradecrossing.dto.request.auth

import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Serializable

@Serializable
@Schema
data class RegisterRequest(
  @field:Schema(name = "profile_pic", description = "프로필 사진 URL", example = "https://example.com/profile.jpg")
  val profilePic: String?,

  @field:Schema(name = "username", description = "유저명", example = "Delly")
  val username: String,

  @field:Schema(name = "island_name", description = "섬 이름", example = "Delly's Island")
  val islandName: String,

  @field:Schema(name = "introduction", description = "자기소개", example = "안녕하세요! 델리입니다.")
  val introduction: String
)