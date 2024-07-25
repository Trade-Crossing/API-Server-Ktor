package com.tradecrossing.api.inquiry

import com.tradecrossing.dto.request.inquiry.InquiryRequest
import com.tradecrossing.service.InquiryService
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
import org.koin.ktor.ext.inject

fun Route.inquiryRouting() {

  val inquiryService by inject<InquiryService>()

  withAuth(TokenType.ACCESS) {
    get<InquiryResource>(InquiryResource.getList) {
      val userId = call.getUserId()
      val inquiries = inquiryService.getInquiries(userId)

      call.respond(HttpStatusCode.OK, inquiries)
    }
    post<InquiryResource>(InquiryResource.post) {
      val userId = call.getUserId()
      val request = call.receive<InquiryRequest.CreateInquiry>()
      val inquiry = inquiryService.addInquiry(request, userId)

      call.respond(HttpStatusCode.Created, inquiry)
    }
  }

}