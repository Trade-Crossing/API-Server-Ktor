package com.tradecrossing.domain.tables.trade

object ItemTradeTable : BaseTradeTable("item_trade") {

  val tradeType = enumerationByName<ItemTradeType>("trade_type", 10)
  val itemCategory = reference("item_category", ItemCategoryTable.id)
  val itemSource = reference("source", SourceTable.id)
  val itemName = varchar("name", 255)
  val itemNameKr = varchar("name_kr", 255)
  val quantity = integer("quantity")
  val variationId = varchar("variation_id", 10).nullable()

  enum class ItemTradeType {
    buy, sell, manjijak
  }
}