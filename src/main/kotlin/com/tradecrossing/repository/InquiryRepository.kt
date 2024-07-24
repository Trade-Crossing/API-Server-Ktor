package com.tradecrossing.repository

import com.tradecrossing.domain.Inquiry
import com.tradecrossing.domain.ResidentInfo

class InquiryRepository {
  fun saveInquiry(question: String, resident: ResidentInfo): Inquiry {

    val newInquiry = Inquiry.new {
      createdBy = resident
      this.question = question
    }

    return newInquiry
  }
}