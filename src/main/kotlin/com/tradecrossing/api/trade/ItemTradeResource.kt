package com.tradecrossing.api.trade

import com.tradecrossing.domain.tables.trade.ItemTradeTable.ItemTradeType
import com.tradecrossing.dto.response.ErrorResponse
import com.tradecrossing.dto.response.ItemTradeDto
import com.tradecrossing.types.TradeCurrency
import io.github.smiley4.ktorswaggerui.dsl.OpenApiRoute
import io.ktor.http.*
import io.ktor.resources.*

@Resource("/items")
class ItemTrades(
  val cursor: Long = 0,
  val size: Int = 10,
  val name: String? = null,
  val tradeType: ItemTradeType = ItemTradeType.sell,
  val variationId: String? = null,
  val isClosed: Boolean = false,
  val currency: TradeCurrency = TradeCurrency.all,
  val minPrice: Int = 0,
  val maxPrice: Int = Int.MAX_VALUE,
) {

  companion object {
    val doc: OpenApiRoute.() -> Unit = {
      summary = "아이템 거래 목록 조회"
      description = "아이템 거래 목록을 조회합니다."
      tags = listOf("Trades", "ItemTrades")
      request {
        queryParameter<Long>("cursor") {
          description = "조회를 시작할 id 값"
          required = false
          example = 0
        }
        queryParameter<Long>("size") {
          description = "조회할 거래 목록의 크기"
          required = false
          example = 10
        }
        queryParameter<String>("name") {
          description = "아이템 이름"
          required = true
          example = "물고기 떡밥"
        }
        queryParameter<ItemTradeType>("tradeType") {
          description = "거래 유형"
          required = false
          example = ItemTradeType.sell
        }
        queryParameter<String?>("variationId") {
          description = "아이템 색상(리폼) id"
          required = false
          example = "1_0"
        }
        queryParameter<Boolean>("isClosed") {
          description = "거래중인 거래만 조회"
          required = false
          example = false
        }
        queryParameter<TradeCurrency>("currency") {
          description = "거래 통화"
          required = false
          example = TradeCurrency.all
        }
        queryParameter<Int>("minPrice") {
          description = "최소 가격"
          required = false
          example = 0
        }
        queryParameter<Int>("maxPrice") {
          description = "최대 가격"
          required = false
          example = Int.MAX_VALUE
        }
      }

      response {
        HttpStatusCode.OK to {
          body<List<ItemTradeDto>>()
        }
        HttpStatusCode.BadRequest to {
          body<ErrorResponse> {
            example("error", ErrorResponse("bad_request", "파라미터가 잘못 되었습니다."))
          }
        }
      }
    }
  }

  @Resource("/{id}")
  class Id(val trades: ItemTrades = ItemTrades(), val id: Long)
}