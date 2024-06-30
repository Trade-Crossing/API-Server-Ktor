package com.tradecrossing.domain.tables.trade

object VillagerTrades : BaseTrades("villager_trades") {

  val name = varchar("name", 255)
  val tradeType = enumerationByName<VillagerTradeType>("trade_type", 10)
  val purity = enumerationByName<VillagerPurity>("purity", 10)
  val category = reference("category_id", VillagerCategoryTable)
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