package com.tradecrossing.domain.tables.trade

enum class ItemTradeType {
  buy, sell, manjijak
}

object ItemTradeTable : BaseTradeTable("item_trade") {

  val tradeType = enumerationByName<ItemTradeType>("trade_type", 10)
  val itemCategory = reference("item_category", ItemCategoryTable.id)
  val itemSource = reference("source", SourceTable.id)
  val itemName = varchar("name", 255)
  val quantity = integer("quantity")
  val variationIndex = integer("variation_index").nullable()


}