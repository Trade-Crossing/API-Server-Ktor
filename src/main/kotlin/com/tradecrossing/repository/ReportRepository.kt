package com.tradecrossing.repository

import com.tradecrossing.domain.Report
import com.tradecrossing.domain.Reports
import com.tradecrossing.domain.Resident
import io.ktor.server.plugins.*
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.and
import java.util.*

class ReportRepository {

  fun findReportedResidents(reporterId: UUID, offenderId: UUID): List<EntityID<UUID>> {
    return Report.find { Reports.reporterId eq reporterId and (Reports.offenderId eq offenderId) }.map { it.offenderId }
  }

  fun addReport(request: Report.Request) {
    val reporter = Resident.findById(request.reporterId) ?: throw NotFoundException("존재하지 않는 유저입니다.")
    val offender = Resident.findById(request.offenderId) ?: throw NotFoundException("존재하지 않는 유저입니다.")

    Report.new {
      this.reporter = reporter
      this.offender = offender
      this.reason = request.reason
    }
  }
}