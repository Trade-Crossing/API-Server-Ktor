package com.tradecrossing.dto.response.chat

import com.tradecrossing.domain.ChatRoom
import com.tradecrossing.domain.ResidentInfoDto
import kotlinx.serialization.Serializable

@Serializable
data class ChatRoomResponse(
  val id: Long,
  val receiver: ResidentInfoDto,
) {
  constructor(chatRoom: ChatRoom) : this(
    chatRoom.id.value,
    ResidentInfoDto(chatRoom.receiver),
  )
}
