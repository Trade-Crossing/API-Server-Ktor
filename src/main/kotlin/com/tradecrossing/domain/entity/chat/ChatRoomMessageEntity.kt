package com.tradecrossing.domain.entity.chat

import com.tradecrossing.domain.entity.resident.ResidentEntity
import com.tradecrossing.domain.tables.chat.ChatRoomMessageTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class ChatRoomMessageEntity(id: EntityID<Long>) : LongEntity(id) {
  companion object : LongEntityClass<ChatRoomMessageEntity>(ChatRoomMessageTable)

  var chatRoom by ChatRoomEntity referencedOn ChatRoomMessageTable.chatRoom
  var sender by ResidentEntity referencedOn ChatRoomMessageTable.sender
  var message by ChatRoomMessageTable.message
  var createdAt by ChatRoomMessageTable.createdAt
  var updatedAt by ChatRoomMessageTable.updatedAt
}