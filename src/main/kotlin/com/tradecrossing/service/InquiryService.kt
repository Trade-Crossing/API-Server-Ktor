package com.tradecrossing.service

import com.tradecrossing.dto.request.inquiry.InquiryRequest
import com.tradecrossing.dto.response.inquiry.InquiryResponse
import com.tradecrossing.repository.InquiryRepository
import com.tradecrossing.repository.ResidentRepository
import com.tradecrossing.system.plugins.DatabaseFactory.dbQuery
import io.ktor.server.plugins.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*

class InquiryService : KoinComponent {

  private val inquiryRepository by inject<InquiryRepository>()
  private val residentRepository by inject<ResidentRepository>()

  suspend fun addInquiry(request: InquiryRequest.CreateInquiry, userId: UUID): InquiryResponse.InquiryInfo = dbQuery {
    val residentInfo = residentRepository.findResidentInfoById(userId) ?: throw NotFoundException("Resident not found")
    val newInquiry = inquiryRepository.saveInquiry(request.question, residentInfo)

    newInquiry.let(InquiryResponse::InquiryInfo)
  }
}