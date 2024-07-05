package com.tradecrossing.service

import ChatMessageResponse
import com.tradecrossing.domain.ChatMessage
import com.tradecrossing.dto.response.chat.ChatRoomResponse
import com.tradecrossing.repository.ChatRepository
import com.tradecrossing.system.plugins.DatabaseFactory.dbQuery
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*

class ChatService : KoinComponent {
  private val chatRepository by inject<ChatRepository>()

  suspend fun findChatRoomExist(id: Long) = dbQuery { chatRepository.findChatRoomExist(id) }

  suspend fun findIsParticipant(id: Long, userId: UUID) = dbQuery { chatRepository.findIsParticipant(id, userId) }

  suspend fun findAllChatRooms(userId: UUID): List<ChatRoomResponse> = dbQuery {
    chatRepository.findAllChatRooms(userId).map { ChatRoomResponse(it) }
  }

  suspend fun createChatRoom(userId: UUID): ChatRoomResponse =
    dbQuery { chatRepository.createChatRoom(userId).let { ChatRoomResponse(it) } }

  suspend fun deleteChatRoom(id: Long, userId: UUID) = dbQuery {
    val isParticipant: Boolean = chatRepository.findIsParticipant(id, userId)

    if (isParticipant) {
      chatRepository.deleteChatRoom(id)
    } else {
      throw Exception("You are not a participant of this chat room")
    }
  }

  suspend fun findAllMessages(id: Long, userId: UUID, cursor: Long? = null): List<ChatMessageResponse> {
    val isParticipant: Boolean = dbQuery { chatRepository.findIsParticipant(id, userId) }

    if (!isParticipant) {
      throw Exception("You are not a participant of this chat room")
    }

    val messages: List<ChatMessage> = dbQuery { chatRepository.findMessages(id, cursor) }

    return messages.map { ChatMessageResponse(it) }
  }

  suspend fun addMessage(id: Long, userId: UUID, message: String) = dbQuery {
    val isParticipant: Boolean = chatRepository.findIsParticipant(id, userId)

    if (isParticipant) {
      chatRepository.addMessage(id, userId, message)
    } else {
      throw Exception("You are not a participant of this chat room")
    }
  }
}