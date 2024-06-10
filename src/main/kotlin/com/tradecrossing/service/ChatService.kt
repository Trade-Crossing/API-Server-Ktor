package com.tradecrossing.service

import com.tradecrossing.domain.entity.chat.ChatRoomEntity
import com.tradecrossing.dto.response.ChatRoomResponse
import com.tradecrossing.repository.ChatRepository
import com.tradecrossing.system.plugins.DatabaseFactory.dbQuery
import java.util.UUID

class ChatService(private val chatRepository: ChatRepository) {

  suspend  fun findUserChats(residentId:UUID) : List<ChatRoomResponse> {
    val chatRooms: List<ChatRoomEntity> = dbQuery { chatRepository.findAll(residentId) }

    return chatRooms.map { ChatRoomResponse(it) }
  }

  suspend fun createNewChatRoom(sender:UUID, reciever: UUID): ChatRoomResponse {
    val chatRoom: ChatRoomEntity = dbQuery { chatRepository.createChatRoom(sender, reciever) }

    return ChatRoomResponse(chatRoom)
  }
}