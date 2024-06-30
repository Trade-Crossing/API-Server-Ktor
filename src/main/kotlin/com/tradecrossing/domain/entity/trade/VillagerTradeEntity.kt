package com.tradecrossing.domain.entity.trade

import com.tradecrossing.domain.ResidentInfo
import com.tradecrossing.domain.tables.trade.BaseTrade
import com.tradecrossing.domain.tables.trade.VillagerTrades
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.time.LocalDateTime

class VillagerTradeEntity(id: EntityID<Long>) : BaseTrade(id) {
  companion object : LongEntityClass<VillagerTradeEntity>(VillagerTrades)

  var name by VillagerTrades.name
  var tradeType by VillagerTrades.tradeType
  var purity by VillagerTrades.purity
  var category by VillagerCategoryEntity referencedOn VillagerTrades.category
  var gender by VillagerTrades.gender
  override var resident: ResidentInfo by ResidentInfo referencedOn VillagerTrades.resident
  override var closed: Boolean by VillagerTrades.closed
  override var bellPrice: Int? by VillagerTrades.bellPrice
  override var milePrice: Int? by VillagerTrades.milePrice
  override var isDeleted: Boolean by VillagerTrades.isDeleted
  override var createdAt: LocalDateTime by VillagerTrades.createdAt
  override var updatedAt: LocalDateTime by VillagerTrades.updatedAt


  override fun toString(): String =
    "VillagerTradeEntity(id=$id, name=$name, tradeType=$tradeType, purity=$purity, category=$category, gender=$gender" + super.toString()

}