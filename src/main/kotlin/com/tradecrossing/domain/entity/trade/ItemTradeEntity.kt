package com.tradecrossing.domain.entity.trade

import com.tradecrossing.domain.entity.resident.ResidentInfoEntity
import com.tradecrossing.domain.tables.trade.ItemTradeTable
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.time.LocalDateTime

class ItemTradeEntity(id: EntityID<Long>) : BaseTradeEntity(id) {
  // read
  var name by ItemTradeTable.itemName
  var tradeType by ItemTradeTable.tradeType
  var category by ItemCategoryEntity referencedOn ItemTradeTable.itemCategory
  var source by SourceEntity referencedOn ItemTradeTable.itemSource
  var quantity by ItemTradeTable.quantity
  var variationId by ItemTradeTable.variationId
  override var resident: ResidentInfoEntity by ResidentInfoEntity referencedOn ItemTradeTable.resident
  override var bellPrice: Int? by ItemTradeTable.bellPrice
  override var milePrice: Int? by ItemTradeTable.milePrice
  override var closed: Boolean by ItemTradeTable.closed
  override var isDeleted: Boolean by ItemTradeTable.isDeleted
  override var createdAt: LocalDateTime by ItemTradeTable.createdAt
  override var updatedAt: LocalDateTime by ItemTradeTable.updatedAt

  companion object : LongEntityClass<ItemTradeEntity>(ItemTradeTable)

  override fun toString(): String {
    return "ItemTradeEntity(" +
        "id=$id, " +
        "resident=$resident, " +
        "closed=$closed, " +
        "bellPrice=$bellPrice, " +
        "milePrice=$milePrice, " +
        "isDeleted=$isDeleted, " +
        "createdAt=$createdAt, " +
        "updatedAt=$updatedAt, " +
        "tradeType=$tradeType, " +
        "category=$category, " +
        "source=$source)"
  }
}