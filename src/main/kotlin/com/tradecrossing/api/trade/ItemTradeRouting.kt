package com.tradecrossing.api.trade

import com.tradecrossing.dto.request.trade.TradeQuery.ItemTradeQuery
import com.tradecrossing.service.TradeService
import com.tradecrossing.system.plugins.withAuth
import com.tradecrossing.types.TokenType
import io.github.smiley4.ktorswaggerui.dsl.resources.delete
import io.github.smiley4.ktorswaggerui.dsl.resources.get
import io.github.smiley4.ktorswaggerui.dsl.resources.patch
import io.github.smiley4.ktorswaggerui.dsl.resources.post
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.itemTrades() {
  val tradeService by inject<TradeService>()
  get<ItemTrades>(ItemTrades.get) { query ->
    require(query.name != null) { "이름은 필수입니다." }
    val queryParam = ItemTradeQuery(query)
    val response = tradeService.findItemTradeList(queryParam, query.cursor, query.size)

    call.respond(HttpStatusCode.OK, response)
  }
  get<ItemTrades.Id>(ItemTrades.Id.get) { trade ->
    val response = tradeService.findItemTradeById(trade.id)

    call.respond(HttpStatusCode.OK, response)
  }

  withAuth(TokenType.ACCESS) {
    post<ItemTrades>(ItemTrades.post) { }
    patch<ItemTrades.Id>(ItemTrades.Id.patch) { trade -> }
    delete<ItemTrades.Id>(ItemTrades.Id.delete) { trade -> }
  }
}

