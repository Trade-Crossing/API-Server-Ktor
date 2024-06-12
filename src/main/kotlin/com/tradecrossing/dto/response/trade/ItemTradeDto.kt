package com.tradecrossing.dto.response.trade

import com.tradecrossing.domain.entity.trade.ItemTradeEntity
import com.tradecrossing.domain.tables.trade.ItemTradeType
import com.tradecrossing.dto.response.resident.ResidentInfoDto
import com.tradecrossing.types.LocalDateTimeSerializer
import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Contextual

import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class ItemTradeDto(
  val id: Long,
  val creator: ResidentInfoDto,
  val closed: Boolean,
  @field:Schema(name = "bell_price")
  val bellPrice: Int?,
  @field:Schema(name = "mile_price")
  val milePrice: Int?,
  @field:Schema(name = "is_deleted")
  val isDeleted: Boolean,
  @Contextual
  @field:Schema(name = "created_at")
  val createdAt: LocalDateTime,
  @Contextual
  @field:Schema(name = "updated_at")
  val updatedAt: LocalDateTime,
  @field:Schema(name = "trade_type")
  val tradeType: ItemTradeType,
  val category: CategoryDto,
  val source: SourceDto,
  val name: String,
  val quantity: Int,
  @field:Schema(name = "variation_index")
  val variationIndex: Int?
) {
  constructor(itemTrade: ItemTradeEntity) : this(
    id = itemTrade.id.value,
    creator = ResidentInfoDto(itemTrade.resident),
    closed = itemTrade.closed,
    bellPrice = itemTrade.bellPrice,
    milePrice = itemTrade.milePrice,
    isDeleted = itemTrade.isDeleted,
    createdAt = itemTrade.createdAt,
    updatedAt = itemTrade.updatedAt,
    tradeType = itemTrade.tradeType,
    category = CategoryDto(itemTrade.category),
    source = SourceDto(itemTrade.source),
    name = itemTrade.name,
    quantity = itemTrade.quantity,
    variationIndex = itemTrade.variationIndex
  )
}
