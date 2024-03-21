package com.tradecrossing.domain.entity.trade

import com.tradecrossing.domain.entity.resident.ResidentInfoEntity
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.id.EntityID
import java.time.LocalDateTime

abstract class BaseTradeEntity(id: EntityID<Long>) : LongEntity(id) {
  abstract var resident: ResidentInfoEntity
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