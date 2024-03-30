package com.tradecrossing.api.trade

import com.tradecrossing.dto.request.trade.TradeQuery
import com.tradecrossing.dto.request.trade.VillagerTradeRequest
import com.tradecrossing.service.TradeService
import com.tradecrossing.system.plugins.getUserId
import com.tradecrossing.system.plugins.withAuth
import com.tradecrossing.types.TokenType
import io.github.smiley4.ktorswaggerui.dsl.resources.delete
import io.github.smiley4.ktorswaggerui.dsl.resources.get
import io.github.smiley4.ktorswaggerui.dsl.resources.patch
import io.github.smiley4.ktorswaggerui.dsl.resources.post
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.villagerRouting() {
  val tradeService by inject<TradeService>()

  get<VillagerTradeResource.TradeList>(VillagerTradeResource.list) { queryParam ->
    val query = TradeQuery.VillagerTradeQuery(queryParam)
    val response = tradeService.findVillagerTradeList(query, queryParam.cursor, queryParam.size)

    call.respond(HttpStatusCode.OK, response)
  }

  get<VillagerTradeResource.Id>(VillagerTradeResource.detail) {
    val response = tradeService.findVillagerTradeById(it.id)
    call.respond(HttpStatusCode.OK, response)
  }

  withAuth(TokenType.ACCESS) {
    post<VillagerTradeResource.Create>(VillagerTradeResource.create) {
      val body = call.receive<VillagerTradeRequest>()
      val userId = call.getUserId()
      val response = tradeService.createVillagerTrade(userId, body)

      call.respond(HttpStatusCode.Created, response)
    }

    patch<VillagerTradeResource.Id>(VillagerTradeResource.update) {
      val body = call.receive<VillagerTradeRequest>()
      val userId = call.getUserId()
      val response = tradeService.updateVillagerTrade(it.id, userId, body)

      call.respond(HttpStatusCode.OK, response)
    }

    delete<VillagerTradeResource.Id>(VillagerTradeResource.delete) {
      val userId = call.getUserId()
      tradeService.deleteVillagerTrade(it.id, userId)

      call.respond(HttpStatusCode.OK)
    }
  }
}