package com.tradecrossing.domain

import com.tradecrossing.domain.VillagerTrades.VillagerGender
import com.tradecrossing.domain.VillagerTrades.VillagerTradeType
import com.tradecrossing.types.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.time.LocalDateTime


class VillagerTrade(id: EntityID<Long>) : BaseTrade(id) {
  companion object : LongEntityClass<VillagerTrade>(VillagerTrades)

  var name by VillagerTrades.name
  var tradeType by VillagerTrades.tradeType
  var purity by VillagerTrades.purity
  var category by VillagerCategory referencedOn VillagerTrades.category
  var gender by VillagerTrades.gender
  override var resident: ResidentInfo by ResidentInfo referencedOn VillagerTrades.resident
  override var closed: Boolean by VillagerTrades.closed
  override var bellPrice: Int? by VillagerTrades.bellPrice
  override var milePrice: Int? by VillagerTrades.milePrice
  override var isDeleted: Boolean by VillagerTrades.isDeleted
  override var createdAt: LocalDateTime by VillagerTrades.createdAt
  override var updatedAt: LocalDateTime by VillagerTrades.updatedAt


  override fun toString(): String =
    "VillagerTradeEntity(id=$id, name=$name, tradeType=$tradeType, purity=$purity, category=$category, gender=$gender" + super.toString()


}

object VillagerTrades : BaseTrades("villager_trades") {

  val name = varchar("name", 255)
  val tradeType = enumerationByName<VillagerTradeType>("trade_type", 10)
  val purity = enumerationByName<VillagerPurity>("purity", 10)
  val category = reference("category_id", VillagerCategorys)
  val gender = enumerationByName<VillagerGender>("gender", 10)


  enum class VillagerTradeType {
    adopt, sale
  }

  enum class VillagerPurity {
    pure, impure
  }

  enum class VillagerGender {
    male, female
  }
}

@Serializable
data class VillagerTradeDto(
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
  @Serializable(with = LocalDateTimeSerializer::class)
  val createdAt: LocalDateTime,
  @Serializable(with = LocalDateTimeSerializer::class)
  val updatedAt: LocalDateTime
) {
  constructor(entity: VillagerTrade) : this(
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