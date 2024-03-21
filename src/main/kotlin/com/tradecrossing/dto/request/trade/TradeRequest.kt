package com.tradecrossing.dto.request.trade

import com.tradecrossing.domain.tables.trade.ItemTradeTable.ItemTradeType
import com.tradecrossing.types.TradeCurrency
import kotlinx.serialization.Serializable

@Serializable
abstract class TradeRequest {
  abstract val name: String
  abstract val currency: TradeCurrency
  abstract val price: Int?
}

@Serializable
data class ItemTradeRequest(
  override val name: String,
  override val currency: TradeCurrency,
  override val price: Int?,
  val tradeType: ItemTradeType,
  val variationId: String?,
  val quantity: Int,
  val itemCategory: String,
  val itemSource: String,
) : TradeRequest() {

  //init {
  //  require(quantity >= 0) { "수량은 0 이상이여야 합니다." }
  //  if (currency == TradeCurrency.donate) require(price == null) { "나눔은 가격이 없어야 합니다." }
  //  else require(price != null) { "가격은 필수입니다." }
  //}
}