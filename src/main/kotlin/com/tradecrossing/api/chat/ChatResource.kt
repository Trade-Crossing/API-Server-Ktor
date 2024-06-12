package com.tradecrossing.api.chat

import com.tradecrossing.dto.request.chat.CreateChatRequest
import com.tradecrossing.dto.response.chat.ChatRoomResponse
import com.tradecrossing.dto.response.ErrorResponse
import io.github.smiley4.ktorswaggerui.dsl.OpenApiRoute
import io.ktor.http.*
import io.ktor.resources.*
import java.util.*

@Resource("/chat")
class ChatResource {

  @Resource("/{id}")
  class Id(val chat: ChatResource = ChatResource(), val id: UUID, val cursor: Long? , val size:Int =10) {

    companion object {
      val get: OpenApiRoute.() -> Unit = {
        summary = "채팅방 조회"
        description = "채팅방의 채팅 목록을 조회합니다."
        tags = listOf("채팅")
        securitySchemeName = "Jwt"
        protected = true
        request {
          queryParameter("id", UUID::class)
          queryParameter<Long?>("cursor")
          queryParameter("size", Int::class) {
            description = "한 번에 가져올 채팅 수"
            example = 10
          }
        }
      }
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

      response {
        HttpStatusCode.Created to {
          body<ChatRoomResponse>()
          description = "성공"
        }
        HttpStatusCode.Unauthorized to {
          body<ErrorResponse>()
          description = "인증에 실패했습니다."
        }
      }
    }
    val delete: OpenApiRoute.() -> Unit = {}
  }
}