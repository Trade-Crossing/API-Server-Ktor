package com.tradecrossing.api.chat

import io.github.smiley4.ktorswaggerui.dsl.OpenApiRoute
import io.ktor.resources.*

@Resource("/chat")
class ChatResource {

  @Resource("/{id}")
  class Id(val chat: ChatResource = ChatResource(), val id: String)
  companion object {
    val get: OpenApiRoute.() -> Unit = {}
    val post: OpenApiRoute.() -> Unit = {}
    val delete: OpenApiRoute.() -> Unit = {}
  }
}