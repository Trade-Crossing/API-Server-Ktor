package com.tradecrossing.api.trade

import com.tradecrossing.dto.request.trade.TradeQuery
import com.tradecrossing.system.plugins.withAuth
import com.tradecrossing.types.TokenType
import io.github.smiley4.ktorswaggerui.dsl.resources.delete
import io.github.smiley4.ktorswaggerui.dsl.resources.get
import io.github.smiley4.ktorswaggerui.dsl.resources.patch
import io.github.smiley4.ktorswaggerui.dsl.resources.post
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.villagerRouting() {
  get<VillageTradeResource.List>(VillageTradeResource.list) { queryParam ->
    val query = TradeQuery.VillagerTradeQuery(queryParam)
    call.respondText("List, query: $query")
  }
  get<VillageTradeResource.Id>(VillageTradeResource.detail) {}

  withAuth(TokenType.ACCESS) {
    post<VillageTradeResource.Create>(VillageTradeResource.create) {}
    patch<VillageTradeResource.Id>(VillageTradeResource.update) {}
    delete<VillageTradeResource.Id>(VillageTradeResource.delete) {}
  }
}