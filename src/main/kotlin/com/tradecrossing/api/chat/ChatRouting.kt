package com.tradecrossing.api.chat

import ChatMessageResponse
import com.tradecrossing.api.chat.ChatResource.Companion.post
import com.tradecrossing.service.ChatService
import com.tradecrossing.system.plugins.getUserId
import com.tradecrossing.system.plugins.json
import com.tradecrossing.system.plugins.withAuth
import com.tradecrossing.types.TokenType
import com.tradecrossing.types.WebSocketMessage
import io.github.smiley4.ktorswaggerui.dsl.resources.delete
import io.github.smiley4.ktorswaggerui.dsl.resources.get
import io.github.smiley4.ktorswaggerui.dsl.resources.post
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import org.koin.ktor.ext.inject
import java.util.*

fun Route.chat() {

  val chatService by inject<ChatService>()



  withAuth(TokenType.ACCESS) {
    get<ChatResource> {
      val userId = call.getUserId()
      val chatRooms = chatService.findAllChatRooms(userId)

      call.respond(chatRooms)
    }

    post<ChatResource>(post) {
      val userId = call.getUserId()
      val newChatRoom = chatService.createChatRoom(userId)

      call.respond(HttpStatusCode.Created, newChatRoom)
    }

    get<ChatResource.Id>(ChatResource.Id.get) { chatRoom ->
      val userId = call.getUserId()
      val messages: List<ChatMessageResponse> = chatService.findAllMessages(chatRoom.id, userId, chatRoom.cursor)

      call.respond(HttpStatusCode.OK, messages)
    }

    delete<ChatResource.Id>({}) {}

  }

  val sessions = Collections.synchronizedMap(mutableMapOf<Long, MutableSet<WebSocketSession>>())
  webSocket("/chat/{chatId}") {
    val userId: UUID = call.getUserId()

    // chatId가 null이거나 숫자가 아닌 경우 close
    val chatId = call.parameters["chatId"]?.toLongOrNull() ?: return@webSocket close(
      CloseReason(
        CloseReason.Codes.CANNOT_ACCEPT,
        "Invalid chatId"
      )
    )

    // chatRoom이 존재하지 않거나 참가자가 아닌 경우 close
    val chatRoomExists: Boolean = chatService.findChatRoomExist(chatId)
    val isParticipant: Boolean = chatService.findIsParticipant(chatId, userId)

    if (!chatRoomExists || !isParticipant) {
      close(CloseReason(CloseReason.Codes.CANNOT_ACCEPT, "Chat room not found or you are not a participant"))
    }

    // chatId에 해당하는 세션을 sessions에 추가
    if (sessions.containsKey(chatId)) {
      sessions[chatId]!!.add(this)
    } else {
      setOf(this)
    }

    try {

      for (frame in incoming) {
        when (frame) {
          is Frame.Ping -> {
            send(Frame.Pong("Pong".toByteArray()))
          }

          is Frame.Pong -> {
            send(Frame.Ping("Ping".toByteArray()))
          }

          is Frame.Close -> {
            sessions[chatId]?.remove(this)
            close(CloseReason(CloseReason.Codes.NORMAL, "Session closed"))
          }

          is Frame.Text -> {
            val string = frame.readText()
            val message = json.decodeFromString<WebSocketMessage>(string)

            chatService.addMessage(chatId, userId, message.message)

            // 메시지를 chatId에 해당하는 세션들에게 전송
            sessions[chatId]!!.filter { it != this }.forEach { sessions ->
              sessions.send(frame)
            }
          }

          else -> {}
        }
      }
    } catch (e: ClosedReceiveChannelException) {
      sessions[chatId]?.remove(this)
    }
  }
}