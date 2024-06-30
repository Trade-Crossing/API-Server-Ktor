package com.tradecrossing.types

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDateTime

class LocalDateTimeSerializer : KSerializer<LocalDateTime> {
  override val descriptor: SerialDescriptor
    get() = PrimitiveSerialDescriptor("LocalDateTime", PrimitiveKind.STRING)

  override fun serialize(encoder: Encoder, value: LocalDateTime) {
    encoder.encodeString(value.toString())
  }

  override fun deserialize(decoder: Decoder): LocalDateTime {
    return LocalDateTime.parse(decoder.decodeString())
  }
}