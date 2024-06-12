package com.tradecrossing.service

import com.tradecrossing.domain.entity.chat.ChatRoomEntity
import com.tradecrossing.dto.response.chat.ChatMessageResponse
import com.tradecrossing.dto.response.chat.ChatRoomResponse
import com.tradecrossing.repository.ChatRepository
import com.tradecrossing.system.plugins.DatabaseFactory.dbQuery
import java.util.UUID

class ChatService(private val chatRepository: ChatRepository) {

  suspend  fun findUserChats(residentId:UUID) : List<ChatRoomResponse> {
    val chatRooms: List<ChatRoomEntity> = dbQuery { chatRepository.findAll(residentId) }

    return chatRooms.map { ChatRoomResponse(it) }
  }

  suspend fun createNewChatRoom(sender:UUID, receiver: UUID): ChatRoomResponse {
    val chatRoom: ChatRoomEntity = dbQuery { chatRepository.createChatRoom(sender, receiver) }

    return ChatRoomResponse(chatRoom)
  }

  suspend fun findChatsOfChatRoom(chatRoomId: UUID): List<ChatMessageResponse> =
    dbQuery { chatRepository.findChats(chatRoomId) }.map { ChatMessageResponse(it) }

}