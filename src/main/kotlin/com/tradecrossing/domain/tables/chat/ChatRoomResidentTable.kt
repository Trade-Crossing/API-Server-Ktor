package com.tradecrossing.domain.tables.chat

import com.tradecrossing.domain.tables.resident.ResidentTable
import org.jetbrains.exposed.dao.id.LongIdTable

object ChatRoomResidentTable : LongIdTable("chat_room_resident") {
  val participant1 = reference("participant1", ResidentTable.id)
  val participant2 = reference("participant2", ResidentTable.id)
}