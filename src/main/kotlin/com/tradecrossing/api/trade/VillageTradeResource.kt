package com.tradecrossing.api.trade

import com.tradecrossing.domain.tables.trade.VillagerTradeTable.VillagerPurity
import com.tradecrossing.domain.tables.trade.VillagerTradeTable.VillagerTradeType
import com.tradecrossing.types.TradeCurrency
import io.github.smiley4.ktorswaggerui.dsl.OpenApiRoute
import io.ktor.resources.*

@Resource("/villagers")
class VillageTradeResource {

  @Resource("")
  class List(
    val parent: VillageTradeResource = VillageTradeResource(),
    val name: String,
    val tradeType: VillagerTradeType,
    val category: String,
    val purity: VillagerPurity,
    val currency: TradeCurrency,
    val price: Int? = null,
    val isClosed: Boolean = false,
    val cursor: Long = 0,
    val size: Int = 10
  )

  @Resource("")
  class Create(val parent: VillageTradeResource = VillageTradeResource())

  @Resource("/{id}")
  class Id(val parent: VillageTradeResource = VillageTradeResource(), val id: Int)

  companion object {
    val list: OpenApiRoute.() -> Unit = {}
    val detail: OpenApiRoute.() -> Unit = {}
    val create: OpenApiRoute.() -> Unit = {}
    val update: OpenApiRoute.() -> Unit = {}
    val delete: OpenApiRoute.() -> Unit = {}
  }
}