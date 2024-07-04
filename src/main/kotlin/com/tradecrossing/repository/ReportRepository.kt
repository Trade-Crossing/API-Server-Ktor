package com.tradecrossing.repository

import com.tradecrossing.domain.Report
import com.tradecrossing.domain.Resident
import io.ktor.server.plugins.*

class ReportRepository {

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