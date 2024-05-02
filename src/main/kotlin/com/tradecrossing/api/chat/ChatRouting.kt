package com.tradecrossing.api.chat

import com.tradecrossing.system.plugins.withAuth
import com.tradecrossing.types.TokenType
import io.github.smiley4.ktorswaggerui.dsl.resources.delete
import io.github.smiley4.ktorswaggerui.dsl.resources.get
import io.github.smiley4.ktorswaggerui.dsl.resources.post
import io.ktor.server.routing.*

fun Route.chat() {

  withAuth(TokenType.ACCESS) {
    get<ChatResource>({}) {}
    post<ChatResource>({}) {}
    delete<ChatResource.Id>({}) {}
  }
}