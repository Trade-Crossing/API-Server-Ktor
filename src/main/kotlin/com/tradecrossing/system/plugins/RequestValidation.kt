package com.tradecrossing.system.plugins

import com.tradecrossing.dto.request.oauth.MobileLoginRequest
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*

fun Application.configureRequestValidation() {
  install(RequestValidation) {

    val reasonList = mutableListOf<String>()
    validate<MobileLoginRequest> { request ->
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
  }
}