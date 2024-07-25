package com.tradecrossing.repository

import com.tradecrossing.domain.Inquiries
import com.tradecrossing.domain.Inquiry
import com.tradecrossing.domain.ResidentInfo
import java.util.*

class InquiryRepository {
  fun saveInquiry(question: String, resident: ResidentInfo): Inquiry {

    val newInquiry = Inquiry.new {
      createdBy = resident
      this.question = question
    }

    return newInquiry
  }

  fun findInquiriesByResidentId(userId: UUID): List<Inquiry> {
    return Inquiry.find { Inquiries.createdBy eq userId }.toList()
  }
}