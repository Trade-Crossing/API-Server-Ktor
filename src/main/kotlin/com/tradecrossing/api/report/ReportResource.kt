package com.tradecrossing.api.report

import com.tradecrossing.domain.Report
import io.github.smiley4.ktorswaggerui.dsl.OpenApiRoute
import io.ktor.http.*
import io.ktor.resources.*

@Resource("/report")
class ReportResource {

  companion object {
    val post: OpenApiRoute.() -> Unit = {
      tags = listOf("신고")
      summary = "신고하기"
      securitySchemeName = "Jwt"
      protected = true
      request {
        body<Report.Request>()
      }

      response {
        HttpStatusCode.OK to {
          description = "신고 성공"
        }
        HttpStatusCode.NotFound to {
          description = "존재하지 않는 유저입니다."
        }
        HttpStatusCode.Unauthorized to {
          description = "인증되지 않았습니다. 필요합니다."
        }
      }
    }
  }
}