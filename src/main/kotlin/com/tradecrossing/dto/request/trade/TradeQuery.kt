package com.tradecrossing.dto.request.trade

import com.tradecrossing.api.trade.ItemTrades
import com.tradecrossing.domain.tables.trade.ItemTradeTable.ItemTradeType
import com.tradecrossing.types.TradeCurrency

sealed class TradeQuery(
  open val closed: Boolean,
  open val currency: TradeCurrency,
  open val minPrice: Int?,
  open val maxPrice: Int?,
) {

  data class ItemTradeQuery(
    val name: String,
    val tradeType: ItemTradeType = ItemTradeType.sell,
    val variationId: String?,
    override val closed: Boolean,
    override val currency: TradeCurrency,
    override val minPrice: Int = 0,
    override val maxPrice: Int = Int.MAX_VALUE,
  ) : TradeQuery(closed, currency, minPrice, maxPrice) {
    constructor(queryParam: ItemTrades) :
        this(
          queryParam.name!!,
          queryParam.tradeType,
          queryParam.variationId,
          queryParam.isClosed,
          queryParam.currency,
          queryParam.minPrice,
          queryParam.maxPrice
        )
  }
}