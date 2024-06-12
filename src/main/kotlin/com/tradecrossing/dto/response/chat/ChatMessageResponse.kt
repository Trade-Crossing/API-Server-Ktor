package com.tradecrossing.dto.response.chat

import com.tradecrossing.domain.entity.chat.ChatRoomMessageEntity
import com.tradecrossing.types.LocalDateTimeSerializer
import com.tradecrossing.types.UUIDSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.util.*

@Serializable
data class ChatMessageResponse(
  @Serializable(with = UUIDSerializer::class)
  val sender: UUID,
  val message: String,
  @Serializable(with = LocalDateTimeSerializer::class)
  val createdAt: LocalDateTime,
) {

  constructor(entity: ChatRoomMessageEntity) :this (
    sender = entity.sender.id.value,
    message = entity.message,
    createdAt = entity.createdAt
  )
}
