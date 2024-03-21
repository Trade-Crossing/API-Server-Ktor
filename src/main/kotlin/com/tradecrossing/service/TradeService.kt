package com.tradecrossing.service

import com.tradecrossing.domain.entity.resident.ResidentInfoEntity
import com.tradecrossing.domain.entity.trade.ItemCategoryEntity
import com.tradecrossing.domain.entity.trade.ItemTradeEntity
import com.tradecrossing.domain.entity.trade.SourceEntity
import com.tradecrossing.domain.entity.trade.VillagerTradeEntity
import com.tradecrossing.domain.tables.resident.ResidentInfoTable
import com.tradecrossing.domain.tables.trade.*
import com.tradecrossing.dto.request.trade.ItemTradeRequest
import com.tradecrossing.dto.request.trade.TradeQuery.ItemTradeQuery
import com.tradecrossing.dto.request.trade.TradeQuery.VillagerTradeQuery
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
import org.jetbrains.exposed.sql.selectAll
import java.util.*

class TradeService {

  // =============================== 1. 아이템 거래 ===============================

  /**
   * 아이템 거래 목록을 조회합니다.
   * @param query 조회할 거래 목록의 정보
   * @param cursor 조회를 시작할 id 값
   * @param size 조회할 거래 목록의 크기
   * @return 조회된 거래 목록
   */
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

  /**
   * 아이템 거래를 조회합니다.
   * @param id 조회할 거래의 id
   * @return 조회된 거래
   */
  suspend fun findItemTradeById(id: Long) = dbQuery {
    val itemTrade =
      ItemTradeEntity.findById(id)?.load(ItemTradeEntity::resident, ItemTradeEntity::source, ItemTradeEntity::category)
        ?: throw NotFoundException("존재하지 않는 거래글입니다.")

    ItemTradeDto(itemTrade)
  }

  suspend fun createItemTrade(request: ItemTradeRequest, residentId: UUID) = dbQuery {

    val resident = ResidentInfoEntity.findById(residentId) ?: throw NotFoundException("존재하지 않는 유저입니다.")
    val source = SourceEntity.find { SourceTable.name eq request.itemSource }.firstOrNull()
      ?: throw NotFoundException("존재하지 않는 출처입니다.")
    val category = ItemCategoryEntity.find { ItemCategoryTable.name eq request.itemCategory }.firstOrNull()
      ?: throw NotFoundException("존재하지 않는 카테고리입니다.")

    val newItemTrade = ItemTradeEntity.new {
      name = request.name
      tradeType = request.tradeType
      this.category = category
      this.source = source
      quantity = request.quantity
      variationId = request.variationId
      this.resident = resident
      when (request.currency) {
        TradeCurrency.bell -> bellPrice = request.price
        TradeCurrency.mile -> milePrice = request.price
        TradeCurrency.all -> {
          bellPrice = request.price
          milePrice = request.price
        }

        TradeCurrency.donate -> {}
      }
    }

    ItemTradeDto(newItemTrade)
  }


  // =============================== 2. 주민 거래 ===============================
  suspend fun findVillagerTradeList(query: VillagerTradeQuery, cursor: Long, size: Int) = dbQuery {

    val priceFilter = when (query.currency) {
      TradeCurrency.bell -> VillagerTradeTable.bellPrice.between(query.minPrice, query.maxPrice)
      TradeCurrency.mile -> VillagerTradeTable.milePrice.between(query.minPrice, query.maxPrice)
      TradeCurrency.all -> VillagerTradeTable.bellPrice.between(
        query.minPrice,
        query.maxPrice
      ) and VillagerTradeTable.milePrice.between(query.minPrice, query.maxPrice)

      TradeCurrency.donate -> VillagerTradeTable.bellPrice.isNull() and VillagerTradeTable.milePrice.isNull()
    }

    val tradeList = VillagerTradeTable
      .leftJoin(ResidentInfoTable)
      .leftJoin(VillagerCategoryTable)
      .selectAll()
      .where {
        (VillagerTradeTable.id greater cursor) and
            (VillagerTradeTable.name eq query.name) and
            (VillagerTradeTable.purity eq query.purity) and
            (VillagerTradeTable.tradeType eq query.tradeType) and
            (VillagerCategoryTable.name eq query.category) and
            (VillagerTradeTable.closed eq query.closed) and
            priceFilter
      }.limit(size).map { VillagerTradeEntity.wrapRow(it) }.toList()
  }
}