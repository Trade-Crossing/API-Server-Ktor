package com.tradecrossing.dto.response.chat

import com.tradecrossing.domain.ChatRoom
import com.tradecrossing.types.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class ChatRoomResponse(
  val id: Long,
  @Serializable(with = UUIDSerializer::class)
  val receiver: UUID?,
) {
  constructor(chatRoom: ChatRoom) : this(
    chatRoom.id.value,
    chatRoom.receiverId?.value,
  )
}
