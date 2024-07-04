package com.tradecrossing.domain

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable


class VillagerCategory(id: EntityID<Int>) : IntEntity(id) {
  companion object : IntEntityClass<VillagerCategory>(VillagerCategorys)

  var name by VillagerCategorys.name
  var krName by VillagerCategorys.krName

  override fun toString(): String {
    return "VillagerCategoryEntity(id=$id, name=$name, krName=$krName)"
  }
}

object VillagerCategorys : IntIdTable("villager_category", "id") {

  val name = varchar("name", 255)
  val krName = varchar("kr_name", 255)
}