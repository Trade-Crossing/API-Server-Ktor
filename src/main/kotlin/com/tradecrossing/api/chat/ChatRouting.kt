package com.tradecrossing.api.chat

import com.tradecrossing.api.chat.ChatResource.Companion.get
import com.tradecrossing.api.chat.ChatResource.Companion.post
import com.tradecrossing.system.plugins.withAuth
import com.tradecrossing.types.TokenType
import io.github.smiley4.ktorswaggerui.dsl.resources.delete
import io.github.smiley4.ktorswaggerui.dsl.resources.get
import io.github.smiley4.ktorswaggerui.dsl.resources.post
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*

fun Route.chat() {


  withAuth(TokenType.ACCESS) {
    get<ChatResource>(get) {

      call.respond(HttpStatusCode.OK)
    }

    post<ChatResource>(post) {


      call.respond(HttpStatusCode.Created)
    }

    get<ChatResource.Id>(ChatResource.Id.get) {


      call.respond(HttpStatusCode.OK)
    }

    delete<ChatResource.Id>({}) {}

  }

  webSocket("/chat/{chatRoomId}") {
  }
}