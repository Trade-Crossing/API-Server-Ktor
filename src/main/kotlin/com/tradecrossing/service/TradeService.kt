package com.tradecrossing.service

import com.tradecrossing.domain.entity.trade.ItemTradeEntity
import com.tradecrossing.domain.tables.trade.ItemTradeTable
import com.tradecrossing.dto.request.trade.TradeQuery.ItemTradeQuery
import com.tradecrossing.dto.response.ItemTradeDto
import com.tradecrossing.system.plugins.DatabaseFactory.dbQuery
import io.ktor.server.plugins.*
import org.jetbrains.exposed.dao.with
import org.jetbrains.exposed.sql.and

class TradeService {

  suspend fun findItemTradeList(query: ItemTradeQuery, cursor: Long, size: Int) = dbQuery {
    val itemTradeList = ItemTradeEntity.find {
      (ItemTradeTable.id greaterEq cursor) and
          (ItemTradeTable.itemName eq query.name) and
          (ItemTradeTable.tradeType eq query.tradeType) and
          (ItemTradeTable.variationId eq query.variationId) and
          (ItemTradeTable.closed eq query.closed) and
          (ItemTradeTable.bellPrice greaterEq query.minPrice) and
          (ItemTradeTable.bellPrice lessEq query.maxPrice)
    }.limit(size, 0).with(ItemTradeEntity::resident).map { ItemTradeDto(it) }.toList()

    itemTradeList
  }

  suspend fun findItemTradeById(id: Long) = dbQuery {
    val itemTrade = ItemTradeEntity.findById(id) ?: throw NotFoundException("존재하지 않는 거래글입니다.")

    ItemTradeDto(itemTrade)
  }
}