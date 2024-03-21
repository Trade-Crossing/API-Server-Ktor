package com.tradecrossing.domain.entity.trade

import com.tradecrossing.domain.entity.resident.ResidentInfoEntity
import com.tradecrossing.domain.tables.trade.VillagerTradeTable
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.time.LocalDateTime

class VillagerTradeEntity(id: EntityID<Long>) : BaseTradeEntity(id) {
  companion object : LongEntityClass<VillagerTradeEntity>(VillagerTradeTable)

  var name by VillagerTradeTable.name
  var tradeType by VillagerTradeTable.tradeType
  var purity by VillagerTradeTable.purity
  var category by VillagerTradeTable.category
  var gender by VillagerTradeTable.gender
  override var resident: ResidentInfoEntity by ResidentInfoEntity referencedOn VillagerTradeTable.resident
  override var closed: Boolean by VillagerTradeTable.closed
  override var bellPrice: Int? by VillagerTradeTable.bellPrice
  override var milePrice: Int? by VillagerTradeTable.milePrice
  override var isDeleted: Boolean by VillagerTradeTable.isDeleted
  override var createdAt: LocalDateTime by VillagerTradeTable.createdAt
  override var updatedAt: LocalDateTime by VillagerTradeTable.updatedAt


  override fun toString(): String =
    "VillagerTradeEntity(id=$id, name=$name, tradeType=$tradeType, purity=$purity, category=$category, gender=$gender" + super.toString()

}