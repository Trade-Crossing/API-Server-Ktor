package com.tradecrossing.domain

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object ChatMessages : LongIdTable("chat_message") {
  val sender = reference("sender", Residents)
  val chatRoom = reference("chat_room", ChatRooms)
  val message = text("message")
  val sendAt = datetime("send_at").clientDefault { LocalDateTime.now() }
}

class ChatMessage(id: EntityID<Long>) : LongEntity(id) {
  companion object : LongEntityClass<ChatMessage>(ChatMessages)

  var sender by Resident referencedOn ChatMessages.sender
  var senderId by ChatMessages.sender

  var chatRoom by ChatRoom referencedOn ChatMessages.chatRoom
  var chatRoomId by ChatMessages.chatRoom
  var message by ChatMessages.message
  var sendAt by ChatMessages.sendAt
}

