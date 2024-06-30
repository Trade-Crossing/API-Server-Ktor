package com.tradecrossing.domain

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

abstract class BaseTrade(id: EntityID<Long>) : LongEntity(id) {
  abstract var resident: ResidentInfo
  abstract var closed: Boolean
  abstract var bellPrice: Int?
  abstract var milePrice: Int?
  abstract var isDeleted: Boolean
  abstract var createdAt: LocalDateTime
  abstract var updatedAt: LocalDateTime

  fun update() {
    updatedAt = LocalDateTime.now()
  }

  override fun toString(): String =
    "resident=$resident, closed=$closed, bellPrice=$bellPrice, milePrice=$milePrice, isDeleted=$isDeleted, createdAt=$createdAt, updatedAt=$updatedAt"
}

open class BaseTrades(tableName: String) : LongIdTable(tableName, "id") {

  val resident = reference("resident", ResidentInfos.id)
  val closed = bool("closed").default(false)
  val bellPrice = integer("bell_price").nullable()
  val milePrice = integer("mile_price").nullable()
  val isDeleted = bool("is_deleted").default(false)
  val createdAt = datetime("created_at").clientDefault { LocalDateTime.now() }
  val updatedAt = datetime("updated_at").clientDefault { (LocalDateTime.now()) }
  val availFrom = datetime("avail_from").clientDefault { LocalDateTime.now() }
  val availTo =
    datetime("avail_to").clientDefault {
      LocalDateTime.now().plusHours(1)
    }
}