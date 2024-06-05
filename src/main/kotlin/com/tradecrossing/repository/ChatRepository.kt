package com.tradecrossing.repository

import com.tradecrossing.domain.entity.chat.ChatRoomEntity
import com.tradecrossing.domain.entity.chat.ChatRoomResidentEntity
import com.tradecrossing.domain.tables.chat.ChatRoomResidentTable
import com.tradecrossing.domain.tables.chat.ChatRoomTable
import com.tradecrossing.dto.response.ChatRoomResponse
import com.tradecrossing.system.plugins.DatabaseFactory
import com.tradecrossing.system.plugins.DatabaseFactory.dbQuery
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.SizedIterable
import org.jetbrains.exposed.sql.or
import java.util.UUID

class ChatRepository {


  fun findAll(userId:UUID): List<ChatRoomEntity> {
    val result: List<EntityID<Long>> =
      ChatRoomResidentEntity.find { (ChatRoomResidentTable.participant1 eq userId) or (ChatRoomResidentTable.participant2 eq userId) }
        .map { it.id }

    return ChatRoomEntity.find { ChatRoomTable.participants inList result }.toList()
  }
}