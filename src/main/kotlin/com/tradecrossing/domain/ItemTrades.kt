package com.tradecrossing.domain

import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.time.LocalDateTime

enum class ItemTradeType {
  buy, sell, manjijak
}


class ItemTradeEntity(id: EntityID<Long>) : BaseTrade(id) {
  // read
  var name by ItemTrades.itemName
  var tradeType by ItemTrades.tradeType
  var category by ItemCategory referencedOn ItemTrades.itemCategory
  var source by Source referencedOn ItemTrades.itemSource
  var quantity by ItemTrades.quantity
  var variationIndex by ItemTrades.variationIndex
  override var resident: ResidentInfo by ResidentInfo referencedOn ItemTrades.resident
  override var bellPrice: Int? by ItemTrades.bellPrice
  override var milePrice: Int? by ItemTrades.milePrice
  override var closed: Boolean by ItemTrades.closed
  override var isDeleted: Boolean by ItemTrades.isDeleted
  override var createdAt: LocalDateTime by ItemTrades.createdAt
  override var updatedAt: LocalDateTime by ItemTrades.updatedAt

  companion object : LongEntityClass<ItemTradeEntity>(ItemTrades)

  override fun toString(): String = "ItemTradeEntity(id=$id, " +
      "name=$name, " +
      "tradeType=$tradeType, " +
      "category=$category, " +
      "source=$source, " +
      "quantity=$quantity, " +
      "variationIndex=$variationIndex, " +
      "resident=$resident, " +
      "bellPrice=$bellPrice, " +
      "milePrice=$milePrice, " +
      "closed=$closed, " +
      "isDeleted=$isDeleted, " +
      "createdAt=$createdAt, " +
      "updatedAt=$updatedAt)"
}


object ItemTrades : BaseTrades("item_trade") {

  val tradeType = enumerationByName<ItemTradeType>("trade_type", 10)
  val itemCategory = reference("item_category", ItemCategorys.id)
  val itemSource = reference("source", Sources.id)
  val itemName = varchar("name", 255)
  val quantity = integer("quantity")
  val variationIndex = integer("variation_index").nullable()


}