package com.tradecrossing.domain.entity.chat

import com.tradecrossing.domain.tables.chat.ChatRoomResidentTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class ChatRoomResidentEntity(id: EntityID<Long>) : LongEntity(id) {
  companion object : LongEntityClass<ChatRoomResidentEntity>(ChatRoomResidentTable)

  var participant1 by ChatRoomResidentTable.participant1
  var participant2 by ChatRoomResidentTable.participant2
}