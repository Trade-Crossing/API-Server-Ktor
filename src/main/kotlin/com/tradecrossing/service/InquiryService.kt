package com.tradecrossing.service

import com.tradecrossing.repository.InquiryRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class InquiryService : KoinComponent {

  private val inquiryRepository by inject<InquiryRepository>()
}