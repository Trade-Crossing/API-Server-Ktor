package com.tradecrossing.api.chat

import com.tradecrossing.api.chat.ChatResource.Companion.get
import com.tradecrossing.api.chat.ChatResource.Companion.post
import com.tradecrossing.dto.request.chat.CreateChatRequest
import com.tradecrossing.service.ChatService
import com.tradecrossing.system.plugins.getUserId
import com.tradecrossing.system.plugins.withAuth
import com.tradecrossing.types.TokenType
import io.github.smiley4.ktorswaggerui.dsl.resources.get
import io.github.smiley4.ktorswaggerui.dsl.resources.post
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import org.koin.ktor.ext.inject
import java.util.*

fun Route.chat() {

  val chatService by inject<ChatService>()

  withAuth(TokenType.ACCESS) {
    get<ChatResource>(get) {
      val userId = call.getUserId()
      val chatRooms = chatService.findResidentChatRooms(userId)

      call.respond(chatRooms)
    }

    post<ChatResource>(post) {
      val userId = call.getUserId()
      val body = call.receive<CreateChatRequest>()

      val chatRoomId = chatService.createChatRoom(userId, body.receiver)

      call.respond(HttpStatusCode.Created, chatRoomId)
    }

  }

  get<ChatResource.Id>(ChatResource.Id.get) { chat ->
    val userId = call.getUserId()
    val result = chatService.findChatRoomMessages(chat.id, chat.cursor, chat.size)

    call.respond(result)
  }


  val sessions = Collections.synchronizedMap(mutableMapOf<Long, List<WebSocketSession>>())
  webSocket("/chat/{id}") {
    val id = call.parameters["id"]?.toLongOrNull() ?: return@webSocket close(
      CloseReason(
        CloseReason.Codes.CANNOT_ACCEPT,
        "Invalid chat room id"
      )
    )
    val chatRoomExist = chatService.checkChatRoomExist(id)

    if (!chatRoomExist) {
      close(CloseReason(CloseReason.Codes.CANNOT_ACCEPT, "Chat room not found"))
      return@webSocket
    }

    if (sessions.containsKey(id)) {
      sessions[id] = sessions[id]!! + this
    } else {
      sessions[id] = listOf(this)
    }

    try {
      for (frame in incoming) {

        if (frame is Frame.Text) {
          val text = frame.readText()
          sessions[id]!!.filter { it != this }.forEach { it.send(text) }
        }

      }
    } catch (e: RuntimeException) {
      sessions[id] = sessions[id]!! - this
    }
  }
}
