package com.tradecrossing.domain.tables.trade

import com.tradecrossing.domain.tables.resident.ResidentTable
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

open class BaseTradeTable(override val tableName: String) : LongIdTable(tableName, "id") {

  val resident = reference("resident", ResidentTable.id)
  val closed = bool("closed").default(false)
  val bellPrice = integer("bell_price").nullable()
  val milePrice = integer("mile_price").nullable()
  val isDeleted = bool("is_deleted").default(false)
  val createdAt = datetime("created_at").default(LocalDateTime.now())
  val updatedAt = datetime("updated_at").default(LocalDateTime.now())
}