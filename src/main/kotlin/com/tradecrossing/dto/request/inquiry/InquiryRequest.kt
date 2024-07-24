package com.tradecrossing.dto.request.inquiry

import kotlinx.serialization.Serializable

class InquiryRequest {

  @Serializable
  data class CreateInquiry(
    val question: String
  )
}