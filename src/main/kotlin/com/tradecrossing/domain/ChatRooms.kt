package com.tradecrossing.domain

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

//Table ChatRoom {
//  id long [pk]
//}

object ChatRooms : LongIdTable("chat_room")

class ChatRoom(id: EntityID<Long>) : LongEntity(id) {
  companion object : LongEntityClass<ChatRoom>(ChatRooms)
}