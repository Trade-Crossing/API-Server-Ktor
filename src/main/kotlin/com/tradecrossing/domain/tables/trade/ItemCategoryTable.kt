package com.tradecrossing.domain.tables.trade

import org.jetbrains.exposed.dao.id.IntIdTable

object ItemCategoryTable : IntIdTable("item_category", "id") {
  val name = varchar("name", 255)
}