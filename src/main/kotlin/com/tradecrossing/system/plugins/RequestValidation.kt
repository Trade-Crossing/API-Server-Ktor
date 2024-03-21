package com.tradecrossing.system.plugins

import com.tradecrossing.dto.request.oauth.MobileLoginRequest
import com.tradecrossing.dto.request.trade.ItemTradeRequest
import com.tradecrossing.types.TradeCurrency
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*

fun Application.configureRequestValidation() {
  install(RequestValidation) {
    validate<MobileLoginRequest> { request ->
      val reasonList = mutableListOf<String>()
      val emailRegex = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}\$")
      if (!emailRegex.matches(request.email)) {
        reasonList.add("이메일 형식이 올바르지 않습니다.")
      }

      if (reasonList.isNotEmpty()) {
        ValidationResult.Invalid(reasonList)
      } else {
        ValidationResult.Valid
      }
    }

    validate<ItemTradeRequest> { request ->
      val reasonList = buildList {
        if (request.name.isEmpty()) add("아이템 이름은 필수입니다.")

        if (request.currency == TradeCurrency.donate) {
          if (request.price != null) add("나눔은 가격을 설정할 수 없습니다.")
        }

        if (request.quantity < 0) add("수량은 0 이상이어야 합니다.")
      }

      if (reasonList.isNotEmpty()) {
        ValidationResult.Invalid(reasonList)
      } else {
        ValidationResult.Valid
      }
    }
  }
}