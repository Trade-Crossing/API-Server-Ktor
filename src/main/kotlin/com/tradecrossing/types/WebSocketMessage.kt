package com.tradecrossing.types

import kotlinx.serialization.Serializable

@Serializable
data class WebSocketMessage(
  val message: String
)
