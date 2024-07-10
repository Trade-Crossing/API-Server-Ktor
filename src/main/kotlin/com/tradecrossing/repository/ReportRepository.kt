package com.tradecrossing.repository

import com.tradecrossing.domain.Report
import com.tradecrossing.domain.Reports
import com.tradecrossing.domain.Resident
import com.tradecrossing.types.TradeCategory
import io.ktor.server.plugins.*
import org.jetbrains.exposed.sql.and

class ReportRepository {

  fun findReportedTradeIds(tradeType: TradeCategory): List<Long> {
    return Report.find { Reports.tradeCategory eq tradeType }.map { it.tradeId }
  }

  fun checkIfReported(tradeType: TradeCategory, id: Long) =
    Report.find { Reports.tradeCategory eq tradeType and (Reports.tradeId eq id) }.count() > 0

  fun addReport(request: Report.Request) {
    val reporter = Resident.findById(request.reporterId) ?: throw NotFoundException("존재하지 않는 유저입니다.")

    Report.new {
      this.reporter = reporter
      this.tradeId = request.tradeId
      this.tradeCategory = request.tradeCategory
      this.reason = request.reason
    }
  }
}