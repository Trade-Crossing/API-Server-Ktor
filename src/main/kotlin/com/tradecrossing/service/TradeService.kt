package com.tradecrossing.service

import com.tradecrossing.domain.entity.trade.ItemTradeEntity
import com.tradecrossing.domain.tables.trade.ItemTradeTable
import com.tradecrossing.dto.request.trade.TradeQuery.ItemTradeQuery
import com.tradecrossing.dto.response.ItemTradeDto
import com.tradecrossing.system.plugins.DatabaseFactory.dbQuery
import com.tradecrossing.types.TradeCurrency
import io.ktor.server.plugins.*
import org.jetbrains.exposed.dao.load
import org.jetbrains.exposed.dao.with
import org.jetbrains.exposed.sql.SqlExpressionBuilder.between
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.isNull
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.andIfNotNull

class TradeService {


  suspend fun findItemTradeList(query: ItemTradeQuery, cursor: Long, size: Int) = dbQuery {
    val currencyFilter = when (query.currency) {
      TradeCurrency.bell ->
        ItemTradeTable.bellPrice.between(query.minPrice, query.maxPrice)

      TradeCurrency.mile -> ItemTradeTable.milePrice.between(query.minPrice, query.maxPrice)

      TradeCurrency.all -> ItemTradeTable.bellPrice.between(
        query.minPrice,
        query.maxPrice
      ) and ItemTradeTable.milePrice.between(query.minPrice, query.maxPrice)

      TradeCurrency.donate -> ItemTradeTable.bellPrice.isNull() and ItemTradeTable.milePrice.isNull()
    }

    val variationFilter = if (query.variationId != null) {
      ItemTradeTable.variationId eq query.variationId
    } else {
      null
    }

    val itemTradeList = ItemTradeEntity.find {
      (ItemTradeTable.id greater cursor) and
          (ItemTradeTable.itemName eq query.name) and
          (ItemTradeTable.tradeType eq query.tradeType) andIfNotNull
          variationFilter and
          (ItemTradeTable.closed eq query.closed) and
          currencyFilter
    }.limit(size).with(ItemTradeEntity::resident, ItemTradeEntity::source, ItemTradeEntity::category)
      .map { ItemTradeDto(it) }.toList()

    itemTradeList
  }

  suspend fun findItemTradeById(id: Long) = dbQuery {
    val itemTrade =
      ItemTradeEntity.findById(id)?.load(ItemTradeEntity::resident, ItemTradeEntity::source, ItemTradeEntity::category)
        ?: throw NotFoundException("존재하지 않는 거래글입니다.")

    ItemTradeDto(itemTrade)
  }
}