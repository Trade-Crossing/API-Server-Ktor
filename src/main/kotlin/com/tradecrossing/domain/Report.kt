package com.tradecrossing.domain

import com.tradecrossing.domain.entity.resident.ResidentEntity
import com.tradecrossing.domain.tables.resident.ResidentTable
import com.tradecrossing.types.ReportStatus
import com.tradecrossing.types.TradeCategory
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime


object Reports : LongIdTable("reports") {
  val reporterId = reference("reporter_id", ResidentTable.id)
  val tradeId = long("trade_id")
  val tradeCategory = enumeration<TradeCategory>("trade_category")
  val reason = text("reason")
  val reportDate = datetime("report_date").clientDefault { LocalDateTime.now() }
  val resolvedTime = datetime("resolved_time").nullable()
  val status = enumeration<ReportStatus>("status").default(ReportStatus.pending)
}

class ReportEntity(id: EntityID<Long>) : LongEntity(id) {
  companion object : LongEntityClass<ReportEntity>(Reports)

  var reporter by ResidentEntity referencedOn Reports.reporterId
  var repoterId by Reports.reporterId
  var tradeId by Reports.tradeId
  var tradeCategory by Reports.tradeCategory
  var reason by Reports.reason
  val reportDate by Reports.reportDate
  var resolvedTime by Reports.resolvedTime
  var status by Reports.status
}