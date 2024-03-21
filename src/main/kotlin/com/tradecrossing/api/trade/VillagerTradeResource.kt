package com.tradecrossing.api.trade

import com.tradecrossing.domain.tables.trade.VillagerTradeTable.VillagerPurity
import com.tradecrossing.domain.tables.trade.VillagerTradeTable.VillagerTradeType
import com.tradecrossing.types.TradeCurrency
import io.github.smiley4.ktorswaggerui.dsl.OpenApiRoute
import io.ktor.resources.*

@Resource("/villagers")
class VillagerTradeResource {

  @Resource("")
  class List(
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
  class Id(val parent: VillagerTradeResource = VillagerTradeResource(), val id: Int)

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
      tags = listOf("거래", "주민 거래")
    }
    val create: OpenApiRoute.() -> Unit = {
      tags = listOf("거래", "주민 거래")
    }
    val update: OpenApiRoute.() -> Unit = {
      tags = listOf("거래", "주민 거래")
    }
    val delete: OpenApiRoute.() -> Unit = {
      tags = listOf("거래", "주민 거래")
    }
  }
}