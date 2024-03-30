package com.tradecrossing.dto.response.resident

import com.tradecrossing.domain.entity.resident.ResidentInfoEntity
import com.tradecrossing.types.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class ResidentInfoDto(
  @Serializable(with = UUIDSerializer::class)
  val id: UUID,
  val username: String,
  val islandName: String,
  val introduction: String,
  val profilePic: String?,
  val defaultProfile: String?,
  val islandCode: String?
) {

  constructor(entity: ResidentInfoEntity) : this(
    entity.id.value,
    entity.username,
    entity.islandName,
    entity.introduction,
    entity.profilePic,
    entity.defaultProfile?.name,
    entity.islandCode
  )
}
