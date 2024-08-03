package com.tradecrossing.repository

import com.tradecrossing.domain.*
import com.tradecrossing.dto.request.trade.ItemTradeRequest
import com.tradecrossing.dto.request.trade.TradeQuery
import com.tradecrossing.system.exceptions.ForbiddenException
import com.tradecrossing.types.TradeCurrency
import io.ktor.server.plugins.*
import org.jetbrains.exposed.dao.load
import org.jetbrains.exposed.sql.SqlExpressionBuilder.between
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.greater
import org.jetbrains.exposed.sql.SqlExpressionBuilder.isNull
import org.jetbrains.exposed.sql.SqlExpressionBuilder.notInSubQuery
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.or
import java.util.*

class TradeRepository {

  fun findItemTradeList(
    query: TradeQuery.ItemTradeQuery,
    cursor: Long,
    size: Int,
    residentId: UUID? = null
  ): List<ItemTrade> {
    val cursorFilter = ItemTrades.id greater cursor
    val nameFilter = ItemTrades.itemName eq query.name
    val tradeTypeFilter = ItemTrades.tradeType eq query.tradeType
    val variationIndexFilter = ItemTrades.variationIndex eq query.variationIndex
    val closedFilter = ItemTrades.closed eq query.closed
    val deletedFilter = ItemTrades.isDeleted eq false
    val currencyFilter = when (query.currency) {
      TradeCurrency.bell -> ItemTrades.bellPrice.between(query.minPrice, query.maxPrice)
      TradeCurrency.mile -> ItemTrades.milePrice.between(query.minPrice, query.maxPrice)
      TradeCurrency.all -> ItemTrades.bellPrice.between(query.minPrice, query.maxPrice) or ItemTrades.milePrice.between(
        query.minPrice,
        query.maxPrice
      )

      TradeCurrency.donate -> ItemTrades.bellPrice.isNull() and ItemTrades.milePrice.isNull()
    }
    val reportedResidentFilter =
      ItemTrades.resident notInSubQuery (Reports.select(Reports.offenderId).where { Reports.reporterId eq residentId })

    val result = ItemTrades.leftJoin(ItemCategorys).leftJoin(Sources).select(ItemTrades.columns).where {
      cursorFilter and reportedResidentFilter and nameFilter and tradeTypeFilter and variationIndexFilter and closedFilter and currencyFilter and deletedFilter
    }.limit(size)

    return ItemTrade.wrapRows(result).toList()

  }

  fun findItemTradeById(id: Long): ItemTrade {
    return ItemTrade.findById(id)?.load(ItemTrade::resident, ItemTrade::source, ItemTrade::category)
      ?: throw Exception("ItemTrade not found")
  }

  fun createItemTrade(request: ItemTradeRequest, residentId: UUID): ItemTrade {
    val resident = ResidentInfo.findById(residentId) ?: throw NotFoundException("존재하지 않는 유저입니다.")
    val source = Source.find { Sources.name eq request.itemSource }.firstOrNull()
      ?: throw NotFoundException("존재하지 않는 출처입니다.")
    val category = ItemCategory.find { ItemCategorys.name eq request.itemCategory }.firstOrNull()
      ?: throw NotFoundException("존재하지 않는 카테고리입니다.")

    var newItemTrade = ItemTrade.new {
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
    newItemTrade.refresh(true)

    return newItemTrade
  }

  fun updateItemTrade(id: Long, request: ItemTradeRequest, residentId: UUID) {
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

  fun deleteItemTrade(id: Long, residentId: UUID) {
    val itemTrade = ItemTrade.findById(id).let {
      if (it == null || it.isDeleted) throw NotFoundException("존재하지 않는 거래글입니다.")
      else if (it.isDeleted) throw NotFoundException("이미 삭제된 거래글입니다.")
      else if (it.resident.id.value != residentId) throw ForbiddenException("본인의 거래글만 삭제할 수 있습니다.")
      it
    }

    itemTrade.isDeleted = true
  }
}