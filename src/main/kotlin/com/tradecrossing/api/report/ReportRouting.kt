package com.tradecrossing.api.report

import com.tradecrossing.domain.Report
import com.tradecrossing.system.plugins.getUserId
import com.tradecrossing.system.plugins.withAuth
import com.tradecrossing.types.TokenType
import io.github.smiley4.ktorswaggerui.dsl.resources.post
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*

fun Route.report() {

  withAuth(TokenType.ACCESS) {
    post<ReportResource>(ReportResource.post) {
      val userId = call.getUserId()
      val body = call.receive<Report.Request>()
    }
  }
}