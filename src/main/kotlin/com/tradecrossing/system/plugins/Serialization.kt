package com.tradecrossing.system.plugins

import com.tradecrossing.types.UUIDSerializer
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy.Builtins.SnakeCase
import kotlinx.serialization.modules.serializersModuleOf

@OptIn(ExperimentalSerializationApi::class)
fun Application.configureSerialization() {
  install(ContentNegotiation) {
    //protobuf(
    //  ProtoBuf {
    //    encodeDefaults = true
    //  }, contentType = ContentType.Application.ProtoBuf
    //)
    json(
      Json {
        prettyPrint = true
        isLenient = true
        ignoreUnknownKeys = true
        namingStrategy = SnakeCase
        encodeDefaults = true
        serializersModuleOf(UUIDSerializer())
      },
      contentType = ContentType.Application.Json
    )
  }

}
