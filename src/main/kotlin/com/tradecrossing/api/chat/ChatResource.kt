package com.tradecrossing.api.chat

import com.tradecrossing.dto.request.chat.CreateChatRequest
import com.tradecrossing.dto.response.ChatRoomResponse
import com.tradecrossing.dto.response.ErrorResponse
import io.github.smiley4.ktorswaggerui.dsl.OpenApiRoute
import io.ktor.http.*
import io.ktor.resources.*

@Resource("/chat")
class ChatResource {

  @Resource("/{id}")
  class Id(val chat: ChatResource = ChatResource(), val id: String) {

    companion object {
      val get: OpenApiRoute.() -> Unit = {}
      val delete: OpenApiRoute.() -> Unit = {}
    }
  }

  companion object {
    val get: OpenApiRoute.() -> Unit = {
      summary = "내 채팅 목록 조회"
      description = "내 채팅 목록을 조회합니다."
      tags = listOf("채팅")
      securitySchemeName = "Jwt"
      protected = true
      response {
        HttpStatusCode.OK to {
          body<List<ChatRoomResponse>>()
          description = "성공"
        }

        HttpStatusCode.Unauthorized to {
          body<ErrorResponse>()
          description = "인증에 실패했습니다."
        }
      }
    }
    val post: OpenApiRoute.() -> Unit = {
      summary = "채팅방 생성"
      description = "채팅방을 생성합니다."
      tags = listOf("채팅")
      securitySchemeName = "Jwt"
      protected = true
      request {
        body<CreateChatRequest>()
      }
    }
    val delete: OpenApiRoute.() -> Unit = {}
  }
}