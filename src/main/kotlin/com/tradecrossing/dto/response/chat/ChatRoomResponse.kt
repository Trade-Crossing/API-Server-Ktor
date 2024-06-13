package com.tradecrossing.dto.response.chat

import com.tradecrossing.domain.entity.chat.ChatRoomEntity
import kotlinx.serialization.Serializable

@Serializable
data class ChatRoomResponse(
  val id: Long
) {
  constructor(entity: ChatRoomEntity) : this(id = entity.id.value)
}
