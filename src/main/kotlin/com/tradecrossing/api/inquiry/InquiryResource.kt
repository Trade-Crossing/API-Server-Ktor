package com.tradecrossing.api.inquiry

import com.tradecrossing.dto.request.inquiry.InquiryRequest
import io.github.smiley4.ktorswaggerui.dsl.OpenApiRoute
import io.ktor.http.*
import io.ktor.resources.*

@Resource("/inquiries")
class InquiryResource {
  companion object {
    val getList: OpenApiRoute.() -> Unit = {}

    val post: OpenApiRoute.() -> Unit = {
      summary = "새로운 문의 생성하기"
      description = "새로운 문의사항을 등록합니다."
      securitySchemeName = "Jwt"
      protected = true
      tags = listOf("문의")

      request {
        body<InquiryRequest.CreateInquiry> {
          description = "문의사항을 등록하기 위한 정보"
          example("예제", InquiryRequest.CreateInquiry("문의사항 내용"))
        }
      }

      response {
        HttpStatusCode.OK to "성공"
        HttpStatusCode.BadRequest to "잘못된 요청"
        HttpStatusCode.Unauthorized to "인증되지 않은 사용자"
      }
    }
  }
}