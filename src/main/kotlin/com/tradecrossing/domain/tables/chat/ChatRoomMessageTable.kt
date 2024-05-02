package com.tradecrossing.domain.tables.chat

import com.tradecrossing.domain.tables.resident.ResidentTable
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object ChatRoomMessageTable : LongIdTable("chat_room_message") {
  val chatRoom = reference("chat_room", ChatRoomTable.id)
  val sender = reference("sender", ResidentTable.id)
  val message = text("message")
  val createdAt = datetime("created_at").clientDefault { LocalDateTime.now() }
  val updatedAt = datetime("updated_at").clientDefault { LocalDateTime.now() }
}