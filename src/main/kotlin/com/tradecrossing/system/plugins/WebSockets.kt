package com.tradecrossing.system.plugins

import io.ktor.client.plugins.websocket.*
import io.ktor.serialization.*
import io.ktor.serialization.kotlinx.*
import io.ktor.server.application.*
import io.ktor.server.websocket.*
import io.ktor.server.websocket.WebSockets
import kotlinx.serialization.json.Json
import java.time.Duration

fun Application.configureWebSockets() {
  install(WebSockets){
    // 핑을 보내는 주기
    pingPeriod = Duration.ofSeconds(15)
    // 핑을 받지 않을 때 연결을 끊는 시간
    timeout = Duration.ofSeconds(15)
    // 최대 프레임 크기
    maxFrameSize = Long.MAX_VALUE
    // 마스킹을 사용하지 않음
    masking = false
    // Kotlinx를 사용하여 Json 인코딩, 디코딩
    contentConverter = KotlinxWebsocketSerializationConverter(Json)

  }
}