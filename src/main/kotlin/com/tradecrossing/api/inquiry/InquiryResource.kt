package com.tradecrossing.api.inquiry

import io.github.smiley4.ktorswaggerui.dsl.OpenApiRoute
import io.ktor.resources.*

@Resource("/inquiries")
class InquiryResource {
  companion object {
    val getList: OpenApiRoute.() -> Unit = {}
    val post: OpenApiRoute.() -> Unit = {}
  }
}