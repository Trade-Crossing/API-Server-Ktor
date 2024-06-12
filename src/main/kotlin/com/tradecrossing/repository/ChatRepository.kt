package com.tradecrossing.repository

import com.tradecrossing.domain.entity.chat.ChatRoomEntity
import com.tradecrossing.domain.entity.chat.ChatRoomMessageEntity
import com.tradecrossing.domain.entity.chat.ChatRoomResidentEntity
import com.tradecrossing.domain.entity.resident.ResidentEntity
import com.tradecrossing.domain.tables.chat.ChatRoomMessageTable
import com.tradecrossing.domain.tables.chat.ChatRoomResidentTable
import com.tradecrossing.domain.tables.chat.ChatRoomTable
import io.ktor.server.plugins.*
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.or
import java.util.UUID

class ChatRepository {


  fun findAll(userId:UUID): List<ChatRoomEntity> {
    val result: List<EntityID<Long>> =
      ChatRoomResidentEntity.find { (ChatRoomResidentTable.participant1 eq userId) or (ChatRoomResidentTable.participant2 eq userId) }
        .map { it.id }

    return ChatRoomEntity.find { ChatRoomTable.participants inList result }.toList()
  }

  fun createChatRoom(sender: UUID, reciever: UUID): ChatRoomEntity {
    val p1 = ResidentEntity.findById(sender) ?: throw NotFoundException("존재하지 않는 유저입니다.")
    val p2 = ResidentEntity.findById(reciever) ?: throw NotFoundException("존재하지 않는 유저입니다.")

    val chatRoom = ChatRoomEntity.new {
      participants = ChatRoomResidentEntity.new {
        participant1 = p1.id
        participant2 = p2.id
      }
    }

    return chatRoom
  }

  fun findChats(chatRoomId: UUID): List<ChatRoomMessageEntity> {
    val chatRoom = ChatRoomEntity.findById(chatRoomId) ?: throw NotFoundException("존재하지 않는 채팅방입니다.")
    val chats = ChatRoomMessageEntity.find { ChatRoomMessageTable.chatRoom eq chatRoom.id }.sortedBy { it.createdAt }

    return chats
  }
}