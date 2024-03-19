package com.tradecrossing.domain.entity.trade

import com.tradecrossing.domain.tables.trade.SourceTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class SourceEntity(id: EntityID<Long>) : LongEntity(id) {
  companion object : LongEntityClass<SourceEntity>(SourceTable)

  var name by SourceTable.name
}