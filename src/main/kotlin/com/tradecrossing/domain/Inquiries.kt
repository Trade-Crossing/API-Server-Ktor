package com.tradecrossing.domain

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.javatime.datetime

class Inquiry(id: EntityID<Long>) : LongEntity(id) {
  var createdBy by ResidentInfo referencedOn Inquiries.createdBy
  var status by Inquiries.status
  var question by Inquiries.question
  var answer by Inquiries.answer
  var resolvedAt by Inquiries.resolvedAt

  var creatorId by Inquiries.createdBy

  companion object : LongEntityClass<Inquiry>(Inquiries)
}

object Inquiries : LongIdTable("inquiry") {
  val createdBy = reference("created_by", ResidentInfos.id)
  val status = enumerationByName<InquiryStatus>("status", 10).clientDefault { InquiryStatus.pending }
  val question = text("question")
  val answer = text("answer").nullable()
  val resolvedAt = datetime("resolved_at").nullable()
}

enum class InquiryStatus {
  pending, resolved
}
