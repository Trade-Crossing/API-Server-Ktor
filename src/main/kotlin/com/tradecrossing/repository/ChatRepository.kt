package com.tradecrossing.repository


import com.tradecrossing.domain.*
import io.ktor.server.plugins.*
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.and
import java.util.*


class ChatRepository {

  fun findAllChatRooms(userId: UUID): List<ChatRoom> {
    val query =
      ChatRooms.leftJoin(ChatRoomParticipants).leftJoin(ResidentInfos).select(ChatRooms.columns + ResidentInfos.columns)
        .where { ChatRoomParticipants.resident eq userId }

    val chatRooms = ChatRoom.wrapRows(query).toList()

    return chatRooms
  }

  fun findChatRoomExist(id: Long): Boolean = ChatRoom.findById(id) != null

  fun createChatRoom(userId: UUID, receiverId: UUID): ChatRoom {
    val resident = Resident.findById(userId) ?: throw NotFoundException("Resident not found")
    val receiver = Resident.findById(receiverId) ?: throw NotFoundException("Receiver not found")
    val chatRoom = ChatRoom.new {
      this.receiver = receiver.info
    }

    val me = ChatRoomParticipant.new {
      this.chatRoom = chatRoom
      this.resident = resident
    }

    val you = ChatRoomParticipant.new {
      this.chatRoom = chatRoom
      this.resident = receiver
    }

    return chatRoom // reload(chatRoom, true)!!
  }

  fun deleteChatRoom(id: Long) {
    ChatRoom.findById(id)?.delete() ?: throw NotFoundException("Chat room not found")
  }

  fun addMessage(id: Long, userId: UUID, message: String) {
    ChatMessage.new {
      this.senderId = EntityID(userId, Residents)
      this.chatRoomId = EntityID(id, ChatRooms)
      this.message = message
    }
  }

  fun findMessages(id: Long, messageId: Long? = null): List<ChatMessage> {
    val messages: List<ChatMessage> =
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

  fun findIsParticipant(chatRoomId: Long, userId: UUID): Boolean {
    return ChatRoomParticipant.find { (ChatRoomParticipants.chatRoom eq chatRoomId) and (ChatRoomParticipants.resident eq userId) }
      .count() > 0
  }


}