package com.tradecrossing.dto.response.trade

import com.tradecrossing.domain.entity.trade.VillagerTradeEntity
import com.tradecrossing.domain.tables.trade.VillagerTradeTable.VillagerGender
import com.tradecrossing.domain.tables.trade.VillagerTradeTable.VillagerTradeType
import com.tradecrossing.dto.response.resident.ResidentInfoDto
import com.tradecrossing.types.LocalDateTimeSerializer
import kotlinx.serialization.Contextual

import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class VillageTradeDto(
  val id: Long,
  val name: String,
  val tradeType: VillagerTradeType,
  val category: String,
  val gender: VillagerGender,
  val resident: ResidentInfoDto,
  val closed: Boolean,
  val bellPrice: Int?,
  val milePrice: Int?,
  val isDeleted: Boolean,
  @Contextual
  val createdAt: LocalDateTime,
  @Contextual
  val updatedAt: LocalDateTime
) {
  constructor(entity: VillagerTradeEntity) : this(
    entity.id.value,
    entity.name,
    entity.tradeType,
    entity.category.name,
    entity.gender,
    ResidentInfoDto(entity.resident),
    entity.closed,
    entity.bellPrice,
    entity.milePrice,
    entity.isDeleted,
    entity.createdAt,
    entity.updatedAt,
  )
}