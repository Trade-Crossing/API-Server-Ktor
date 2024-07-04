package com.tradecrossing.domain

import com.tradecrossing.types.ReportStatus
import com.tradecrossing.types.TradeCategory
import com.tradecrossing.types.UUIDSerializer
import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime
import java.util.*


object Reports : LongIdTable("reports") {
  val reporterId = reference("reporter_id", Residents.id)
  val tradeId = long("trade_id")
  val tradeCategory = enumeration<TradeCategory>("trade_category")
  val reason = text("reason")
  val reportDate = datetime("report_date").clientDefault { LocalDateTime.now() }
  val resolvedTime = datetime("resolved_time").nullable()
  val status = enumeration<ReportStatus>("status").default(ReportStatus.pending)
}

class Report(id: EntityID<Long>) : LongEntity(id) {
  companion object : LongEntityClass<Report>(Reports)

  var reporter by Resident referencedOn Reports.reporterId
  var repoterId by Reports.reporterId
  var tradeId by Reports.tradeId
  var tradeCategory by Reports.tradeCategory
  var reason by Reports.reason
  val reportDate by Reports.reportDate
  var resolvedTime by Reports.resolvedTime
  var status by Reports.status


  @Serializable
  @Schema(name = "Report Request")
  data class Request(
    @Serializable(with = UUIDSerializer::class)
    @field:Schema(description = "신고자", required = true)
    val reporterId: UUID,
    @field:Schema(description = "신고 대상 거래 ID", required = true)
    val tradeId: Long,
    @field:Schema(description = "신고 대상 거래 카테고리", required = true, defaultValue = "item")
    val tradeCategory: TradeCategory,
    @field:Schema(description = "신고 사유", required = true)
    val reason: String
  )
}