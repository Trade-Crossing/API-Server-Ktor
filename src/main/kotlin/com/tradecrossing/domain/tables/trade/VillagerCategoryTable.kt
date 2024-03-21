package com.tradecrossing.domain.tables.trade

import org.jetbrains.exposed.dao.id.IntIdTable

object VillagerCategoryTable : IntIdTable("villager_category", "id") {

  val name = varchar("name", 255)
  val krName = varchar("kr_name", 255)
}