package com.tradecrossing.repository

import com.tradecrossing.domain.ChatRoom
import com.tradecrossing.domain.ChatRoomParticipants
import com.tradecrossing.domain.ChatRooms
import com.tradecrossing.domain.ResidentInfos
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
}