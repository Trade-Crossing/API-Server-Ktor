package com.tradecrossing.domain.entity.chat

import com.tradecrossing.domain.tables.chat.ChatRoomTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class ChatRoomEntity(id: EntityID<UUID>) : UUIDEntity(id) {
  companion object : UUIDEntityClass<ChatRoomEntity>(ChatRoomTable)

  var participants by ChatRoomResidentEntity referencedOn ChatRoomTable.participants
  var createdAt by ChatRoomTable.createdAt
  var updatedAt by ChatRoomTable.updatedAt
}