package com.tradecrossing.domain

import com.tradecrossing.types.LocalDateTimeSerializer
import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.time.LocalDateTime

enum class ItemTradeType {
  buy, sell, manjijak
}


class ItemTrade(id: EntityID<Long>) : BaseTrade(id) {
  // read
  var name by ItemTrades.itemName
  var tradeType by ItemTrades.tradeType
  var category by ItemCategory referencedOn ItemTrades.itemCategory
  var source by Source referencedOn ItemTrades.itemSource
  var quantity by ItemTrades.quantity
  var variationIndex by ItemTrades.variationIndex
  override var resident: ResidentInfo by ResidentInfo referencedOn ItemTrades.resident
  override var bellPrice: Int? by ItemTrades.bellPrice
  override var milePrice: Int? by ItemTrades.milePrice
  override var closed: Boolean by ItemTrades.closed
  override var isDeleted: Boolean by ItemTrades.isDeleted
  override var createdAt: LocalDateTime by ItemTrades.createdAt
  override var updatedAt: LocalDateTime by ItemTrades.updatedAt

  companion object : LongEntityClass<ItemTrade>(ItemTrades)

  override fun toString(): String = "ItemTradeEntity(id=$id, " +
      "name=$name, " +
      "tradeType=$tradeType, " +
      "category=$category, " +
      "source=$source, " +
      "quantity=$quantity, " +
      "variationIndex=$variationIndex, " +
      "resident=$resident, " +
      "bellPrice=$bellPrice, " +
      "milePrice=$milePrice, " +
      "closed=$closed, " +
      "isDeleted=$isDeleted, " +
      "createdAt=$createdAt, " +
      "updatedAt=$updatedAt)"
}


object ItemTrades : BaseTrades("item_trade") {

  val tradeType = enumerationByName<ItemTradeType>("trade_type", 10)
  val itemCategory = reference("item_category", ItemCategorys.id)
  val itemSource = reference("source", Sources.id)
  val itemName = varchar("name", 255)
  val quantity = integer("quantity")
  val variationIndex = integer("variation_index").nullable()

}

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
  @field:Schema(name = "created_at")
  @Serializable(with = LocalDateTimeSerializer::class)
  val createdAt: LocalDateTime,
  @field:Schema(name = "updated_at")
  @Serializable(with = LocalDateTimeSerializer::class)
  val updatedAt: LocalDateTime,
  @field:Schema(name = "trade_type")
  val tradeType: ItemTradeType,
  val category: ItemCategoryDto,
  val source: SourceDto,
  val name: String,
  val quantity: Int,
  @field:Schema(name = "variation_index")
  val variationIndex: Int?
) {
  constructor(itemTrade: ItemTrade) : this(
    id = itemTrade.id.value,
    creator = ResidentInfoDto(itemTrade.resident),
    closed = itemTrade.closed,
    bellPrice = itemTrade.bellPrice,
    milePrice = itemTrade.milePrice,
    isDeleted = itemTrade.isDeleted,
    createdAt = itemTrade.createdAt,
    updatedAt = itemTrade.updatedAt,
    tradeType = itemTrade.tradeType,
    category = ItemCategoryDto(itemTrade.category),
    source = SourceDto(itemTrade.source),
    name = itemTrade.name,
    quantity = itemTrade.quantity,
    variationIndex = itemTrade.variationIndex
  )
}