package com.tradecrossing.service

import com.tradecrossing.domain.*
import com.tradecrossing.dto.request.trade.ItemTradeRequest
import com.tradecrossing.dto.request.trade.TradeQuery.ItemTradeQuery
import com.tradecrossing.dto.request.trade.TradeQuery.VillagerTradeQuery
import com.tradecrossing.dto.request.trade.VillagerTradeRequest
import com.tradecrossing.system.exceptions.ForbiddenException
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
import org.jetbrains.exposed.sql.or
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
        ItemTrades.bellPrice.between(query.minPrice, query.maxPrice)

      TradeCurrency.mile -> ItemTrades.milePrice.between(query.minPrice, query.maxPrice)

      TradeCurrency.all -> ItemTrades.bellPrice.between(
        query.minPrice,
        query.maxPrice
      ) or ItemTrades.milePrice.between(query.minPrice, query.maxPrice)

      TradeCurrency.donate -> ItemTrades.bellPrice.isNull() and ItemTrades.milePrice.isNull()
    }

    val variationFilter = if (query.variationIndex != null) {
      ItemTrades.variationIndex eq query.variationIndex
    } else {
      null
    }

    // 판매/구매 타입
    val tradeTypeFilter = if (query.tradeType == ItemTradeType.sell) {
      ItemTrades.tradeType eq ItemTradeType.sell
    } else {
      ItemTrades.tradeType eq ItemTradeType.buy
    }

    val itemTradeList = ItemTrade.find {
      (ItemTrades.id greater cursor) and
          (ItemTrades.itemName eq query.name) and
          (ItemTrades.tradeType eq query.tradeType) andIfNotNull
          variationFilter and
          tradeTypeFilter and
          (ItemTrades.closed eq query.closed) and
          currencyFilter
    }.limit(size).with(ItemTrade::resident, ItemTrade::source, ItemTrade::category)
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
      ItemTrade.findById(id)?.load(ItemTrade::resident, ItemTrade::source, ItemTrade::category)
        ?: throw NotFoundException("존재하지 않는 거래글입니다.")

    ItemTradeDto(itemTrade)
  }

  suspend fun createItemTrade(request: ItemTradeRequest, residentId: UUID) = dbQuery {

    val resident = ResidentInfo.findById(residentId) ?: throw NotFoundException("존재하지 않는 유저입니다.")
    val source = Source.find { Sources.name eq request.itemSource }.firstOrNull()
      ?: throw NotFoundException("존재하지 않는 출처입니다.")
    val category = ItemCategory.find { ItemCategorys.name eq request.itemCategory }.firstOrNull()
      ?: throw NotFoundException("존재하지 않는 카테고리입니다.")

    val newItemTrade = ItemTrade.new {
      name = request.name
      tradeType = request.tradeType
      this.category = category
      this.source = source
      quantity = request.quantity
      variationIndex = request.variationIndex
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

  suspend fun updateItemTrade(id: Long, request: ItemTradeRequest, residentId: UUID) = dbQuery {
    val source = Source.find { Sources.name eq request.itemSource }.firstOrNull()
      ?: throw NotFoundException("존재하지 않는 출처입니다.")
    val category = ItemCategory.find { ItemCategorys.name eq request.itemCategory }.firstOrNull()
      ?: throw NotFoundException("존재하지 않는 카테고리입니다.")
    val itemTrade = ItemTrade.findById(id).let {
      if (it == null || it.isDeleted) throw NotFoundException("존재하지 않는 거래글입니다.")
      else if (it.isDeleted) throw NotFoundException("이미 삭제된 거래글입니다.")
      else if (it.resident.id.value != residentId) throw ForbiddenException("본인의 거래글만 수정할 수 있습니다.")

      it
    }

    itemTrade.apply {
      name = request.name
      tradeType = request.tradeType
      this.category = category
      this.source = source
      quantity = request.quantity
      variationIndex = request.variationIndex
      this.resident = resident
      when (request.currency) {
        TradeCurrency.bell -> bellPrice = request.price
        TradeCurrency.mile -> milePrice = request.price
        TradeCurrency.all -> {
          bellPrice = request.price
          milePrice = request.price
        }

        TradeCurrency.donate -> {
          bellPrice = null
          milePrice = null
        }
      }
    }
  }

  suspend fun deleteItemTrade(id: Long, residentId: UUID) = dbQuery {
    val itemTrade = ItemTrade.findById(id).let {
      if (it == null) throw NotFoundException("존재하지 않는 거래글입니다.")
      else if (it.isDeleted) throw NotFoundException("이미 삭제된 거래글입니다.")
      else if (it.resident.id.value != residentId) throw ForbiddenException("본인의 거래글만 삭제할 수 있습니다.")

      it
    }

    itemTrade.isDeleted = true
  }


  // =============================== 2. 주민 거래 ===============================
  suspend fun findVillagerTradeList(query: VillagerTradeQuery, cursor: Long, size: Int) = dbQuery {

    val priceFilter = when (query.currency) {
      TradeCurrency.bell -> VillagerTrades.bellPrice.between(query.minPrice, query.maxPrice)
      TradeCurrency.mile -> VillagerTrades.milePrice.between(query.minPrice, query.maxPrice)
      TradeCurrency.all -> VillagerTrades.bellPrice.between(
        query.minPrice,
        query.maxPrice
      ) and VillagerTrades.milePrice.between(query.minPrice, query.maxPrice)

      TradeCurrency.donate -> VillagerTrades.bellPrice.isNull() and VillagerTrades.milePrice.isNull()
    }

    val tradeList = VillagerTrades
      .leftJoin(ResidentInfos)
      .leftJoin(VillagerCategorys)
      .selectAll()
      .where {
        (VillagerTrades.id greater cursor) and
            (VillagerTrades.name eq query.name) and
            (VillagerTrades.purity eq query.purity) and
            (VillagerTrades.tradeType eq query.tradeType) and
            (VillagerCategorys.name eq query.category) and
            (VillagerTrades.closed eq query.closed) and
            priceFilter
      }.limit(size)
      .map { VillagerTradeDto(VillagerTrade.wrapRow(it)) }
      .toList()

    tradeList
  }

  suspend fun findVillagerTradeById(id: Long) = dbQuery {
    val trade = VillagerTrade.findById(id) ?: throw NotFoundException("존재하지 않는 주민 거래입니다.")

    VillagerTradeDto(trade)
  }

  suspend fun createVillagerTrade(userId: UUID, request: VillagerTradeRequest) = dbQuery {
    val resident = ResidentInfo.findById(userId) ?: throw NotFoundException("존재하지 않는 유저입니다.")
    val category = VillagerCategory.find { VillagerCategorys.name eq request.category }.firstOrNull()
      ?: throw NotFoundException("존재하지 않는 카테고리입니다.")

    val newTrade = VillagerTrade.new {
      name = request.name
      tradeType = request.tradeType
      purity = request.purity
      this.category = category
      gender = request.gender
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

    VillagerTradeDto(newTrade)
  }

  suspend fun updateVillagerTrade(id: Long, userId: UUID, request: VillagerTradeRequest) = dbQuery {
    val category: VillagerCategory
    val trade = VillagerTrade.findById(id).let {
      if (it == null || it.isDeleted) throw NotFoundException("존재하지 않는 주민 거래입니다.")
      else if (it.isDeleted) throw NotFoundException("이미 삭제된 주민 거래입니다.")
      else if (it.resident.id.value != userId) throw ForbiddenException("본인의 주민 거래만 수정할 수 있습니다.")

      it
    }
    category = VillagerCategory.find { VillagerCategorys.name eq request.category }.firstOrNull()
      ?: throw NotFoundException("존재하지 않는 카테고리입니다.")

    trade.apply {
      name = request.name
      tradeType = request.tradeType
      purity = request.purity
      gender = request.gender
      this.category = category
      when (request.currency) {
        TradeCurrency.bell -> bellPrice = request.price
        TradeCurrency.mile -> milePrice = request.price
        TradeCurrency.all -> {
          bellPrice = request.price
          milePrice = request.price
        }

        TradeCurrency.donate -> {
          bellPrice = null
          milePrice = null
        }
      }
      update()
    }
  }

  suspend fun deleteVillagerTrade(id: Long, userId: UUID) = dbQuery {}
}