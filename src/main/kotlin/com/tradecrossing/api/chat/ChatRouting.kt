package com.tradecrossing.api.chat

import com.tradecrossing.api.chat.ChatResource.Companion.get
import com.tradecrossing.service.ChatService
import com.tradecrossing.system.plugins.getUserId
import com.tradecrossing.system.plugins.withAuth
import com.tradecrossing.types.TokenType
import io.github.smiley4.ktorswaggerui.dsl.resources.get
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.chat() {

  val chatService by inject<ChatService>()

  withAuth(TokenType.ACCESS) {
    get<ChatResource>(get) {
      val userId = call.getUserId()
      val chatRooms = chatService.findResidentChatRooms(userId)

      call.respond(chatRooms)
    }
  }
}
