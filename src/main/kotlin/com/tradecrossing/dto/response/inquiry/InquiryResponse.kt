package com.tradecrossing.dto.response.inquiry

import com.tradecrossing.domain.Inquiry
import com.tradecrossing.domain.InquiryStatus
import com.tradecrossing.types.LocalDateTimeSerializer
import com.tradecrossing.types.UUIDSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.util.*


class InquiryResponse {
  @Serializable
  data class InquiryInfo(
    val id: Long,

    @Serializable(with = UUIDSerializer::class)
    val createdBy: UUID,

    val question: String,

    val answer: String?,

    val status: InquiryStatus,

    @Serializable(with = LocalDateTimeSerializer::class)
    val resolvedAt: LocalDateTime?
  ) {
    constructor(entity: Inquiry) : this(
      id = entity.id.value,
      createdBy = entity.createdBy.id.value,
      question = entity.question,
      answer = entity.answer,
      status = entity.status,
      resolvedAt = entity.resolvedAt
    )
  }
}