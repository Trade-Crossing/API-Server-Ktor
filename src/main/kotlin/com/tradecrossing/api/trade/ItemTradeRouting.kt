package com.tradecrossing.api.trade

import com.tradecrossing.dto.request.trade.TradeQuery.ItemTradeQuery
import com.tradecrossing.service.TradeService
import com.tradecrossing.system.plugins.withAuth
import com.tradecrossing.types.TokenType
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.routing.patch
import io.ktor.server.routing.post
import org.koin.ktor.ext.inject

fun Route.itemTrades() {
  val tradeService by inject<TradeService>()
  get<ItemTrades> { query ->
    require(query.name != null) { "이름은 필수입니다." }
    val queryParam = ItemTradeQuery(query)
    val response = tradeService.findItemTradeList(queryParam, query.cursor, query.size)

    call.respond(HttpStatusCode.OK, response)
  }
  get<ItemTrades.Id> { trade ->
    val response = tradeService.findItemTradeById(trade.id)

    call.respond(HttpStatusCode.OK, response)
  }

  withAuth(TokenType.ACCESS) {
    post<ItemTrades> { }
    patch<ItemTrades.Id> { trade -> }
    delete<ItemTrades.Id> { trade -> }
  }
}