package com.tradecrossing.api.chat

import ChatMessageResponse
import com.tradecrossing.dto.request.chat.CreateChatRequest
import com.tradecrossing.dto.response.ErrorResponse
import com.tradecrossing.dto.response.chat.ChatRoomResponse
import io.github.smiley4.ktorswaggerui.dsl.OpenApiRoute
import io.ktor.http.*
import io.ktor.resources.*

@Resource("/chats")
class ChatResource {

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
          description = "채팅 목록을 조회합니다."
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
        HttpStatusCode.Accepted to {
          body<ChatRoomResponse>()
          description = "채팅방 생성 성공"
        }

        HttpStatusCode.Unauthorized to {
          body<ErrorResponse>()
          description = "인증에 실패했습니다."
        }
      }
    }
    val delete: OpenApiRoute.() -> Unit = {}
  }


  @Resource("/{id}")
  class Id(val chat: ChatResource = ChatResource(), val id: Long, val cursor: Long?, val size: Int = 20) {

    companion object {
      val get: OpenApiRoute.() -> Unit = {
        summary = "채팅방 조회"
        description = "채팅방의 채팅 목록을 조회합니다."
        tags = listOf("채팅")
        securitySchemeName = "Jwt"
        protected = true
        request {
          queryParameter<Long?>("cursor")
          queryParameter("size", Int::class) {
            description = "한 번에 가져올 채팅 수"
            example = 10
          }
        }

        response {
          HttpStatusCode.OK to {
            body<List<ChatMessageResponse>>()
            description = "채팅 목록을 조회합니다."

          }

          HttpStatusCode.Unauthorized to {
            body<ErrorResponse>()
            description = "인증에 실패했습니다."
          }
        }
      }

      val delete: OpenApiRoute.() -> Unit = {
        summary = "채팅방 삭제"
        description = "채팅방을 삭제합니다."
        tags = listOf("채팅")
        securitySchemeName = "Jwt"
        protected = true
        response {
          HttpStatusCode.OK to {
            description = "채팅방 삭제 성공"
          }


          HttpStatusCode.Unauthorized to {
            body<ErrorResponse>()
            description = "인증에 실패했습니다."
          }
        }
      }
    }
  }
}