package com.tradecrossing.service

import com.tradecrossing.domain.Report
import com.tradecrossing.repository.ReportRepository
import com.tradecrossing.system.plugins.DatabaseFactory.dbQuery
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ReportService : KoinComponent {

  private val reportRepository: ReportRepository by inject()


  suspend fun addReport(request: Report.Request) = dbQuery { reportRepository.addReport(request) }
}