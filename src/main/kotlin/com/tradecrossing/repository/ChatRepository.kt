package com.tradecrossing.repository

import com.tradecrossing.domain.*
import io.ktor.server.plugins.*
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.selectAll
import java.util.*


class ChatRepository {

  fun findAllChatRooms(userId: UUID): List<ChatRoom> {
    val chatRooms =
      ChatRooms.leftJoin(ResidentInfos).selectAll().where {
        ChatRooms.id inSubQuery (ChatRoomParticipants.select(ChatRoomParticipants.chatRoom)
          .where { ChatRoomParticipants.resident eq userId })
      }.let(ChatRoom::wrapRows).toList()

    return chatRooms
  }

  fun createChatRoom(userId: UUID, receiverId: UUID): Long {
    val receiver = ResidentInfo.findById(receiverId) ?: throw NotFoundException("Receiver not found")
    ResidentInfo.findById(userId) ?: throw NotFoundException("Resident not found")

    val newChatRoom = ChatRoom.new {
      this.receiver = receiver
    }

    ChatRoomParticipants.batchInsert(listOf(userId, receiverId)) {
      this[ChatRoomParticipants.resident] = it
      this[ChatRoomParticipants.chatRoom] = newChatRoom.id
    }

    return newChatRoom.id.value
  }
}