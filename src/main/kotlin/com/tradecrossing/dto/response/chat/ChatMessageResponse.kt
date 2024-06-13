package com.tradecrossing.dto.response.chat

import com.tradecrossing.domain.entity.chat.ChatRoomMessageEntity
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.util.*

@Serializable
data class ChatMessageResponse(
  @Contextual
  val sender: UUID,
  val message: String,
  @Contextual
  val createdAt: LocalDateTime,
) {

  constructor(entity: ChatRoomMessageEntity) : this(
    sender = entity.sender.id.value,
    message = entity.message,
    createdAt = entity.createdAt
  )
}
