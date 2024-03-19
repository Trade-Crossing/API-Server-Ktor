package com.tradecrossing.dto.response

import com.tradecrossing.domain.entity.trade.ItemTradeEntity
import com.tradecrossing.domain.tables.trade.ItemTradeTable.ItemTradeType
import com.tradecrossing.dto.response.resident.ResidentInfoDto
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class ItemTradeDto(
  val id: Long,
  val creator: ResidentInfoDto,
  val closed: Boolean,
  val bellPrice: Int?,
  val milePrice: Int?,
  val isDeleted: Boolean,
  val createdAt: LocalDateTime,
  val updatedAt: LocalDateTime,
  val tradeType: ItemTradeType,
  val category: CategoryDto,
  val source: SourceDto,
  val name: String,
  val nameKr: String,
  val quantity: Int,
  val variationId: String?
) {
  constructor(itemTrade: ItemTradeEntity) : this(
    id = itemTrade.id.value,
    creator = ResidentInfoDto(itemTrade.resident),
    closed = itemTrade.closed,
    bellPrice = itemTrade.bellPrice,
    milePrice = itemTrade.milePrice,
    isDeleted = itemTrade.isDeleted,
    createdAt = itemTrade.createdAt.toKotlinLocalDateTime(),
    updatedAt = itemTrade.updatedAt.toKotlinLocalDateTime(),
    tradeType = itemTrade.tradeType,
    category = CategoryDto(itemTrade.category),
    source = SourceDto(itemTrade.source),
    name = itemTrade.name,
    nameKr = itemTrade.nameKr,
    quantity = itemTrade.quantity,
    variationId = itemTrade.variationId
  )
}
