package com.tradecrossing.dto.response.chat

import com.tradecrossing.domain.entity.chat.ChatRoomEntity
import com.tradecrossing.types.UUIDSerializer
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class ChatRoomResponse(
  @Contextual
  val id:UUID) {
  constructor(entity:ChatRoomEntity) :this (id = entity.id.value)
}
