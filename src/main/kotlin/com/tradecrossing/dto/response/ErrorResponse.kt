package com.tradecrossing.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(val code: String, val message: String)
