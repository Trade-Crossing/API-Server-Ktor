package com.tradecrossing.dto.request.trade

import com.tradecrossing.api.trade.ItemTrades
import com.tradecrossing.api.trade.VillagerTradeResource
import com.tradecrossing.domain.ItemTradeType
import com.tradecrossing.domain.VillagerTrades.VillagerPurity
import com.tradecrossing.domain.VillagerTrades.VillagerTradeType
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
    val variationIndex: Int?,
    override val closed: Boolean,
    override val currency: TradeCurrency,
    override val minPrice: Int = 0,
    override val maxPrice: Int = Int.MAX_VALUE,
  ) : TradeQuery(closed, currency, minPrice, maxPrice) {
    constructor(queryParam: ItemTrades) :
        this(
          queryParam.name!!,
          queryParam.tradeType,
          queryParam.variationIndex,
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

    constructor(queryParam: VillagerTradeResource.TradeList) :
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