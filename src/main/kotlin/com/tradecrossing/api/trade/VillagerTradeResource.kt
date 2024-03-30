package com.tradecrossing.api.trade

import com.tradecrossing.domain.tables.trade.VillagerTradeTable.VillagerPurity
import com.tradecrossing.domain.tables.trade.VillagerTradeTable.VillagerTradeType
import com.tradecrossing.dto.request.trade.VillagerTradeRequest
import com.tradecrossing.dto.response.ErrorResponse
import com.tradecrossing.dto.response.trade.VillageTradeDto
import com.tradecrossing.types.TradeCurrency
import io.github.smiley4.ktorswaggerui.dsl.OpenApiRoute
import io.ktor.http.*
import io.ktor.resources.*

@Resource("/villagers")
class VillagerTradeResource {

  @Resource("")
  class TradeList(
    val parent: VillagerTradeResource = VillagerTradeResource(),
    val name: String,
    val tradeType: VillagerTradeType,
    val category: String,
    val purity: VillagerPurity,
    val currency: TradeCurrency,
    val price: Int? = null,
    val isClosed: Boolean = false,
    val cursor: Long = 0,
    val size: Int = 10
  )

  @Resource("")
  class Create(val parent: VillagerTradeResource = VillagerTradeResource())

  @Resource("/{id}")
  class Id(val parent: VillagerTradeResource = VillagerTradeResource(), val id: Long)

  companion object {
    val list: OpenApiRoute.() -> Unit = {
      summary = "주민 거래 목록 조회"
      tags = listOf("거래", "주민 거래")
      description = "주민 거래 목록을 조회합니다."
      request {
        queryParameter<String>("name") {
          description = "주민 이름"
          required = true
          example = "죠니"
        }

        queryParameter<VillagerTradeType>("tradeType") {
          description = "거래 목적"
          required = true
          example = VillagerTradeType.adopt
        }

        queryParameter<String>("category") {
          description = "주민 종족"
          required = true
          example = "cat"
        }

        queryParameter<VillagerPurity>("purity") {
          description = "순종 여부"
          required = false
          example = VillagerPurity.pure
        }

        queryParameter<TradeCurrency>("currency") {
          description = "거래 화폐"
          required = true
          example = TradeCurrency.all
        }

        queryParameter<Int?>("price") {
          description = "거래 가격"
          required = false
          example = 1000
        }

        queryParameter<Boolean>("isClosed") {
          description = "거래 종료 여부"
          required = false
          example = false
        }

        queryParameter<Long>("cursor") {
          description = "페이징 커서"
          required = false
          example = 0
        }

        queryParameter<Int>("size") {
          description = "페이지 크기"
          required = false
          example = 10
        }
      }
    }
    val detail: OpenApiRoute.() -> Unit = {
      summary = "주민 거래 상세 조회"
      description = "주민 거래 상세 정보를 조회합니다."
      tags = listOf("거래", "주민 거래")
      request {
        pathParameter<Long>("id") {
          description = "거래 ID"
          required = true
          example = 1
        }
      }
      response {
        HttpStatusCode.OK to {
          body<VillageTradeDto>()
        }
      }
    }
    val create: OpenApiRoute.() -> Unit = {
      summary = "주민 거래 생성"
      description = "주민 거래를 생성합니다."
      tags = listOf("거래", "주민 거래")
      securitySchemeName = "Jwt"
      protected = true
      request {
        body<VillagerTradeRequest>()
      }
      response {
        HttpStatusCode.Created to {
          body<VillageTradeDto>()
        }
        HttpStatusCode.Unauthorized to {
          body<ErrorResponse>()
        }
        HttpStatusCode.Forbidden to {
          body<ErrorResponse>()
        }
      }
    }
    val update: OpenApiRoute.() -> Unit = {
      summary = "주민 거래 수정"
      description = "주민 거래를 수정합니다."
      tags = listOf("거래", "주민 거래")
      securitySchemeName = "Jwt"
      protected = true
      request {
        pathParameter<Long>("id") {
          description = "거래 ID"
          required = true
          example = 1
        }
        body<VillagerTradeRequest>()
      }
      response {
        HttpStatusCode.OK to {
          body<VillageTradeDto>()
        }
        HttpStatusCode.Unauthorized to {
          body<ErrorResponse>()
        }
        HttpStatusCode.Forbidden to {
          body<ErrorResponse>()
        }
      }
    }
    val delete: OpenApiRoute.() -> Unit = {
      summary = "주민 거래 삭제"
      description = "주민 거래를 삭제합니다."
      tags = listOf("거래", "주민 거래")
      securitySchemeName = "Jwt"
      protected = true
      request {
        pathParameter<Long>("id") {
          description = "거래 ID"
          required = true
          example = 1
        }
      }
      response {
        HttpStatusCode.OK to {
          body<Unit>()
        }
        HttpStatusCode.Unauthorized to {
          body<ErrorResponse>()
        }
        HttpStatusCode.Forbidden to {
          body<ErrorResponse>()
        }
      }
    }
  }
}