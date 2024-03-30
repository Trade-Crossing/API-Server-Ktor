package com.tradecrossing.domain.tables.trade

import com.tradecrossing.domain.tables.resident.ResidentInfoTable
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

open class BaseTradeTable(tableName: String) : LongIdTable(tableName, "id") {

  val resident = reference("resident", ResidentInfoTable.id)
  val closed = bool("closed").default(false)
  val bellPrice = integer("bell_price").nullable()
  val milePrice = integer("mile_price").nullable()
  val isDeleted = bool("is_deleted").default(false)
  val createdAt = datetime("created_at").clientDefault { LocalDateTime.now() }
  val updatedAt = datetime("updated_at").clientDefault { (LocalDateTime.now()) }
  val availFrom = datetime("avail_from").clientDefault { LocalDateTime.now() }
  val availTo = datetime("avail_to").clientDefault { LocalDateTime.now().plusHours(1) }
}