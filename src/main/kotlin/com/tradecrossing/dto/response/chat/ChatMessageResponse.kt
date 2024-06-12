package com.tradecrossing.dto.response.chat

import com.tradecrossing.domain.entity.chat.ChatRoomMessageEntity
import com.tradecrossing.types.UUIDSerializer
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class ChatMessageResponse(
  @Serializable(with = UUIDSerializer::class)
  val sender: UUID,
  val message: String,
  val createdAt: LocalDateTime,
) {

  constructor(entity: ChatRoomMessageEntity) :this (
    sender = entity.sender.id.value,
    message = entity.message,
    createdAt = entity.createdAt.toKotlinLocalDateTime()
  )
}
