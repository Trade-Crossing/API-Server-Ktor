package com.tradecrossing.domain

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object ChatRoomParticipants : LongIdTable("chat_room_participant") {
  val chatRoom = reference("chat_room", ChatRooms)
  val resident = reference("resident", Residents)
  val joinedAt = datetime("joined_at").clientDefault { LocalDateTime.now() }

}

class ChatRoomParticipant(id: EntityID<Long>) : LongEntity(id) {
  companion object : LongEntityClass<ChatRoomParticipant>(ChatRoomParticipants)

  var chatRoom by ChatRoom referencedOn ChatRoomParticipants.chatRoom
  var resident by Resident referencedOn ChatRoomParticipants.resident
  var joinedAt by ChatRoomParticipants.joinedAt
}