package com.tradecrossing.dto.request.chat

import com.tradecrossing.types.UUIDSerializer
import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class CreateChatRequest(
  @Contextual
  @field:Schema(implementation = UUID::class, description = "상대방 id", name = "resident_id")
  val residentId:UUID
)
