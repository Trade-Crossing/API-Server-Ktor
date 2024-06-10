package com.tradecrossing.api.chat

import com.tradecrossing.api.chat.ChatResource.Companion.get
import com.tradecrossing.api.chat.ChatResource.Companion.post
import com.tradecrossing.dto.request.chat.CreateChatRequest
import com.tradecrossing.dto.response.ChatRoomResponse
import com.tradecrossing.service.ChatService
import com.tradecrossing.system.plugins.getUserId
import com.tradecrossing.system.plugins.withAuth
import com.tradecrossing.types.TokenType
import io.github.smiley4.ktorswaggerui.dsl.resources.delete
import io.github.smiley4.ktorswaggerui.dsl.resources.get
import io.github.smiley4.ktorswaggerui.dsl.resources.post
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import java.util.UUID

fun Route.chat() {

  val chatService: ChatService by inject()

  withAuth(TokenType.ACCESS) {
    get<ChatResource>(get) {
      val residentId:UUID = call.getUserId()
      val chatRooms: List<ChatRoomResponse> = chatService.findUserChats(residentId)

      call.respond(HttpStatusCode.OK, chatRooms)
    }
    post<ChatResource>(post) {
      val residentId:UUID = call.getUserId()
      val body = call.receive<CreateChatRequest>()

      val newChatRoom = chatService.createNewChatRoom(residentId, body.residentId)

      call.respond(HttpStatusCode.Created, newChatRoom)
    }
    delete<ChatResource.Id>({}) {}
  }
}