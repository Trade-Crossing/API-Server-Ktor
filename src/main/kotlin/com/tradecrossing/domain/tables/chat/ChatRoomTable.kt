package com.tradecrossing.domain.tables.chat

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object ChatRoomTable : UUIDTable("chat_room") {
  val participants = reference("participants", ChatRoomResidentTable.id)
  val createdAt = datetime("created_at").clientDefault { LocalDateTime.now() }
  val updatedAt = datetime("updated_at").clientDefault { LocalDateTime.now() }
}