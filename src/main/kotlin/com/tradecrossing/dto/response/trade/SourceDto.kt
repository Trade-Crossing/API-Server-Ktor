package com.tradecrossing.dto.response.trade

import com.tradecrossing.domain.entity.trade.SourceEntity
import kotlinx.serialization.Serializable

@Serializable
class SourceDto(val name: String, val krName: String) {
  constructor(source: SourceEntity) : this(source.name, source.krName)

}
