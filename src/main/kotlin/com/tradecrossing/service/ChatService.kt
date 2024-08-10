package com.tradecrossing.service

import ChatMessageResponse
import com.tradecrossing.dto.response.chat.ChatRoomResponse
import com.tradecrossing.repository.ChatRepository
import com.tradecrossing.system.plugins.DatabaseFactory.dbQuery
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*

class ChatService : KoinComponent {
  private val chatRepository by inject<ChatRepository>()

  suspend fun findResidentChatRooms(userId: UUID) = dbQuery {
    chatRepository.findAllChatRooms(userId).map(::ChatRoomResponse)
  }

  suspend fun createChatRoom(userId: UUID, receiverId: UUID) = dbQuery {
    chatRepository.createChatRoom(userId, receiverId)
  }

  suspend fun findChatRoomMessages(chatRoomId: Long, cursor: Long?, size: Int?) = dbQuery {
    val messages = chatRepository.findChatRoomMessages(chatRoomId, cursor, size ?: 20)

    messages.map(::ChatMessageResponse)
  }

  suspend fun checkChatRoomExist(chatRoomId: Long) = dbQuery {
    chatRepository.checkChatRoomExist(chatRoomId)
  }
}