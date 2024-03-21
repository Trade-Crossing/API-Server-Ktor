package com.tradecrossing.domain.entity.trade

import com.tradecrossing.domain.tables.trade.VillagerCategoryTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class VillagerCategoryEntity(id: EntityID<Int>) : IntEntity(id) {
  companion object : IntEntityClass<VillagerCategoryEntity>(VillagerCategoryTable)

  var name by VillagerCategoryTable.name
  var krName by VillagerCategoryTable.krName

  override fun toString(): String {
    return "VillagerCategoryEntity(id=$id, name=$name, krName=$krName)"
  }
}