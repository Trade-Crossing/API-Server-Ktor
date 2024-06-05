package com.tradecrossing.dto.response

import com.tradecrossing.domain.entity.chat.ChatRoomEntity
import com.tradecrossing.types.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class ChatRoomResponse(
  @Serializable(with = UUIDSerializer::class)
  val id:UUID) {
  constructor(entity:ChatRoomEntity) :this (id = entity.id.value)
}
