package com.tradecrossing.dto.request.trade

import com.tradecrossing.api.trade.ItemTrades
import com.tradecrossing.api.trade.VillageTradeResource
import com.tradecrossing.domain.tables.trade.ItemTradeTable.ItemTradeType
import com.tradecrossing.domain.tables.trade.VillagerTradeTable.VillagerPurity
import com.tradecrossing.domain.tables.trade.VillagerTradeTable.VillagerTradeType
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

    init {
      require(minPrice >= 0) { "최고 금액은 0 이상이여야 합니다." }
    }
  }

  data class VillagerTradeQuery(
    val name: String,
    val purity: VillagerPurity,
    val tradeType: VillagerTradeType,
    val category: String,
    override val closed: Boolean,
    override val currency: TradeCurrency,
    override val minPrice: Int = 0,
    override val maxPrice: Int = Int.MAX_VALUE,
  ) : TradeQuery(closed, currency, minPrice, maxPrice) {
    init {
      require(minPrice >= 0) { "최고 금액은 0 이상이여야 합니다." }
    }

    constructor(queryParam: VillageTradeResource.List) :
        this(
          queryParam.name,
          queryParam.purity,
          queryParam.tradeType,
          queryParam.category,
          queryParam.isClosed,
          queryParam.currency,
          queryParam.price ?: 0
        )
  }
}