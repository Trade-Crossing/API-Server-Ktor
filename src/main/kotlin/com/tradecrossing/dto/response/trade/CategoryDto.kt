package com.tradecrossing.dto.response.trade

import com.tradecrossing.domain.entity.trade.ItemCategoryEntity
import kotlinx.serialization.Serializable

@Serializable
class CategoryDto(val name: String, val krName: String) {
  constructor(category: ItemCategoryEntity) : this(category.name, category.krName)
}
