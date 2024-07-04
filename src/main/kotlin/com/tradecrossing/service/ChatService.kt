package com.tradecrossing.service

import com.tradecrossing.dto.response.chat.ChatRoomResponse
import com.tradecrossing.repository.ChatRepository
import com.tradecrossing.system.plugins.DatabaseFactory.dbQuery
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*

class ChatService : KoinComponent {
  private val chatRepository by inject<ChatRepository>()

  suspend fun findAllChatRooms(userId: UUID) = dbQuery {
    chatRepository.findAllChatRooms(userId).map { ChatRoomResponse(it) }
  }

  suspend fun findChatRoom(id: Long) = dbQuery { chatRepository.findChatRoom(id).let { ChatRoomResponse(it) } }

  suspend fun createChatRoom(userId: UUID) =
    dbQuery { chatRepository.createChatRoom(userId).let { ChatRoomResponse(it) } }

  suspend fun deleteChatRoom(id: Long, userId: UUID) = dbQuery {
    val isParticipant = chatRepository.findIsParticipant(id, userId)

    if (isParticipant) {
      chatRepository.deleteChatRoom(id)
    } else {
      throw Exception("You are not a participant of this chat room")
    }
  }

  suspend fun addMessage(id: Long, userId: UUID, message: String) = dbQuery {
    val isParticipant = chatRepository.findIsParticipant(id, userId)

    if (isParticipant) {
      chatRepository.addMessage(id, userId, message)
    } else {
      throw Exception("You are not a participant of this chat room")
    }
  }
}