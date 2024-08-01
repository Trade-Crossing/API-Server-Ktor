package com.tradecrossing.repository

import com.tradecrossing.domain.ChatRoom
import com.tradecrossing.domain.ChatRoomParticipants
import com.tradecrossing.domain.ChatRooms
import com.tradecrossing.domain.ResidentInfos
import org.jetbrains.exposed.sql.JoinType
import java.util.*


class ChatRepository {

  fun findAllChatRooms(userId: UUID): List<ChatRoom> {
    val chatRooms = ChatRoomParticipants.leftJoin(ChatRooms)
      .join(ResidentInfos, joinType = JoinType.LEFT, onColumn = ResidentInfos.id, otherColumn = ChatRooms.receiver)
      .select(ChatRooms.columns)
      .where { ChatRoomParticipants.resident eq userId }.let(ChatRoom::wrapRows).toList()

    return chatRooms
  }
}