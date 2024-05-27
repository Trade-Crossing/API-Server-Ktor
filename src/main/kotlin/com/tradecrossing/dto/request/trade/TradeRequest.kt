package com.tradecrossing.dto.request.trade

import com.tradecrossing.domain.tables.trade.ItemTradeType
import com.tradecrossing.domain.tables.trade.VillagerTradeTable.VillagerGender
import com.tradecrossing.domain.tables.trade.VillagerTradeTable.VillagerPurity
import com.tradecrossing.domain.tables.trade.VillagerTradeTable.VillagerTradeType
import com.tradecrossing.types.TradeCurrency
import io.swagger.v3.oas.annotations.media.Schema
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
  @field:Schema(implementation = TradeCurrency::class)
  override val currency: TradeCurrency,
  override val price: Int?,
  val tradeType: ItemTradeType,
  val variationId: String?,
  val quantity: Int,
  val itemCategory: String,
  val itemSource: String,
) : TradeRequest()

@Serializable
data class VillagerTradeRequest(
  override val name: String,
  @field:Schema(implementation = TradeCurrency::class)
  override val currency: TradeCurrency,
  override val price: Int?,
  val gender: VillagerGender,
  val category: String,
  val tradeType: VillagerTradeType,
  val purity: VillagerPurity,
) : TradeRequest()