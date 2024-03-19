package com.tradecrossing.api.trade

import com.tradecrossing.domain.tables.trade.ItemTradeTable.ItemTradeType
import com.tradecrossing.types.TradeCurrency
import io.ktor.resources.*

@Resource("/items")
data class ItemTrades(
  val cursor: Long = 0,
  val size: Int = 10,
  val tradeType: ItemTradeType = ItemTradeType.sell,
  val variationId: String? = null,
  val isClosed: Boolean = false,
  val currency: TradeCurrency = TradeCurrency.all,
  val minPrice: Int = 0,
  val maxPrice: Int = Int.MAX_VALUE,
) {

  @Resource("{id}")
  data class Id(val trades: ItemTrades = ItemTrades(), val id: Long)
}