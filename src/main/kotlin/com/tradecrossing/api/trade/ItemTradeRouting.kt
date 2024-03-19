package com.tradecrossing.api.trade

import com.tradecrossing.system.plugins.withAuth
import com.tradecrossing.types.TokenType
import io.github.smiley4.ktorswaggerui.dsl.resources.delete
import io.github.smiley4.ktorswaggerui.dsl.resources.get
import io.github.smiley4.ktorswaggerui.dsl.resources.patch
import io.github.smiley4.ktorswaggerui.dsl.resources.post
import io.ktor.server.routing.*

fun Route.itemTrades() {
  get<ItemTrades> { query -> }
  get<ItemTrades.Id> { trade -> }

  withAuth(TokenType.ACCESS) {
    post<ItemTrades> { }
    patch<ItemTrades.Id> { trade -> }
    delete<ItemTrades.Id> { trade -> }
  }
}