package com.tradecrossing.repository


import com.tradecrossing.domain.*
import com.tradecrossing.domain.ChatRoom.Companion.reload
import io.ktor.server.plugins.*
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.and
import java.util.*

class ChatRepository {


  fun findChatRoom(id: Long): ChatRoom = ChatRoom.findById(id) ?: throw NotFoundException("Chat room not found")

  fun createChatRoom(userId: UUID): ChatRoom {
    val resident = Resident.findById(userId) ?: throw NotFoundException("Resident not found")
    val chatRoom = ChatRoom.new { }

    ChatRoomParticipant.new {
      this.chatRoom = chatRoom
      this.resident = resident
    }

    return reload(chatRoom, true)!!
  }

  fun deleteChatRoom(id: Long) {
    ChatRoom.findById(id)?.delete() ?: throw NotFoundException("Chat room not found")
  }

  fun findMessages(id: Long, messageId: Long? = null): List<ChatMessage> {
    val messages =
      (ChatMessages leftJoin Residents).select(
        ChatMessages.columns + Residents.columns
      )
        .apply {
          if (messageId != null) {
            where { ChatMessages.chatRoom eq id and (ChatMessages.id less id) }
          } else {
            where { ChatMessages.chatRoom eq id }
          }
        }
        .limit(20)
        .orderBy(ChatMessages.sendAt to SortOrder.ASC)
        .map { ChatMessage.wrapRow(it) }

    return messages
  }


}