package com.tradecrossing.service

import com.tradecrossing.dto.response.chat.ChatRoomResponse
import com.tradecrossing.repository.ChatRepository
import com.tradecrossing.system.plugins.DatabaseFactory.dbQuery
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*

class ChatService : KoinComponent {
  private val chatRepository by inject<ChatRepository>()

  suspend fun findResidentChatRooms(userId: UUID) = dbQuery {
    val result = chatRepository.findAllChatRooms(userId).map(::ChatRoomResponse)
  }

}