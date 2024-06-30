package com.tradecrossing.domain

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

class ItemCategory(id: EntityID<Int>) : IntEntity(id) {

  companion object : IntEntityClass<ItemCategory>(ItemCategorys)

  var name by ItemCategorys.name
  var krName by ItemCategorys.krName

  override fun toString(): String {
    return "ItemCategoryEntity(id=$id, name=$name)"
  }
}

object ItemCategorys : IntIdTable("item_category", "id") {
  val name = varchar("name", 255)
  val krName = varchar("kr_name", 255)
}