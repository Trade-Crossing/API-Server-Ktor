package com.tradecrossing.domain.entity.trade

import com.tradecrossing.domain.entity.resident.ResidentEntity
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.id.EntityID
import java.time.LocalDateTime

abstract class BaseTradeEntity(id: EntityID<Long>) : LongEntity(id) {

  abstract val resident: ResidentEntity
  abstract var closed: Boolean
  abstract var bellPrice: Int?
  abstract var milePrice: Int?
  abstract var isDeleted: Boolean
  abstract var createdAt: LocalDateTime
  abstract var updatedAt: LocalDateTime
}