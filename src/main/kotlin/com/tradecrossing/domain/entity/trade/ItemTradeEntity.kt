package com.tradecrossing.domain.entity.trade

import com.tradecrossing.domain.entity.resident.ResidentEntity
import com.tradecrossing.domain.tables.trade.ItemTradeTable
import org.jetbrains.exposed.dao.id.EntityID
import java.time.LocalDateTime

class ItemTradeEntity(id: EntityID<Long>) : BaseTradeEntity(id) {
  override val resident: ResidentEntity by ResidentEntity referencedOn ItemTradeTable.resident
  override var closed: Boolean by ItemTradeTable.closed
  override var bellPrice: Int? by ItemTradeTable.bellPrice
  override var milePrice: Int? by ItemTradeTable.milePrice
  override var isDeleted: Boolean by ItemTradeTable.isDeleted
  override var createdAt: LocalDateTime by ItemTradeTable.createdAt
  override var updatedAt: LocalDateTime by ItemTradeTable.updatedAt
  var tradeType by ItemTradeTable.tradeType
  val category by ItemCategoryEntity referencedOn ItemTradeTable.itemCategory
  val source by SourceEntity referencedOn ItemTradeTable.itemSource
  var name by ItemTradeTable.itemName
  var nameKr by ItemTradeTable.itemNameKr
  var quantity by ItemTradeTable.quantity
  var variationId by ItemTradeTable.variationId

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