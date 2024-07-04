package com.tradecrossing.dto.response.chat

import ChatMessageResponse
import com.tradecrossing.domain.ChatRoom

data class ChatRoomResponse(
  val id: Long,
  val messages: List<ChatMessageResponse>
) {
  constructor(chatRoom: ChatRoom) : this(
    chatRoom.id.value,
    kotlin.runCatching { chatRoom.messages.map { ChatMessageResponse(it) } }.getOrDefault(emptyList())
  )
}
