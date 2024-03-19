package com.tradecrossing.dto.response.resident

import com.tradecrossing.domain.entity.resident.ResidentEntity
import com.tradecrossing.types.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class ResidentDto(
  @Serializable(with = UUIDSerializer::class)
  val id: UUID,
  val email: String,
  val provider: String,
  val providerId: String,
  val registered: Boolean,
) {

  constructor(entity: ResidentEntity) : this(
    entity.id.value,
    entity.email,
    entity.provider.name,
    entity.providerId,
    entity.registered
  )
}
