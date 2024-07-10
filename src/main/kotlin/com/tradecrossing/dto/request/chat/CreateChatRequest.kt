package com.tradecrossing.dto.request.chat

import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class CreateChatRequest(
  @Contextual
  @field:Schema(implementation = UUID::class, description = "상대방 id")
  val receiver: UUID
)
