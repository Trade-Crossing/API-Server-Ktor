package com.tradecrossing.domain.entity.chat

import com.tradecrossing.domain.tables.chat.ChatRoomTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class ChatRoomEntity(id: EntityID<Long>) : LongEntity(id) {
  companion object : LongEntityClass<ChatRoomEntity>(ChatRoomTable)

  var participants by ChatRoomResidentEntity referencedOn ChatRoomTable.participants
  var createdAt by ChatRoomTable.createdAt
  var updatedAt by ChatRoomTable.updatedAt
}