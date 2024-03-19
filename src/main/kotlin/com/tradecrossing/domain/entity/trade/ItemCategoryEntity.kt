package com.tradecrossing.domain.entity.trade

import com.tradecrossing.domain.tables.trade.ItemCategoryTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class ItemCategoryEntity(id: EntityID<Int>) : IntEntity(id) {

  companion object : IntEntityClass<ItemCategoryEntity>(ItemCategoryTable)

  var name by ItemCategoryTable.name
  var krName by ItemCategoryTable.krName

  override fun toString(): String {
    return "ItemCategoryEntity(id=$id, name=$name)"
  }
}