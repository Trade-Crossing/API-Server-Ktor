package com.tradecrossing.domain

import com.tradecrossing.types.OAuthProvider
import com.tradecrossing.types.UUIDSerializer
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

class Resident(id: EntityID<UUID>) : UUIDEntity(id) {

  companion object : UUIDEntityClass<Resident>(Residents)

  var email by Residents.email
  var provider by Residents.provider
  var providerId by Residents.providerId
  var registered by Residents.registered


  override fun toString(): String {
    return "ResidentEntity(email='$email', provider=$provider, providerId='$providerId', registered=$registered)"
  }


}

object Residents : UUIDTable("resident", "id") {
  val email = varchar("email", 255).uniqueIndex("resident_email_key")
  val provider = enumerationByName<OAuthProvider>("provider", 20)
  val providerId = varchar("provider_id", 255).uniqueIndex("resident_provider_id_key")
  val registered = bool("registered").default(false)
}

@Serializable
data class ResidentDto(
  @Serializable(with = UUIDSerializer::class)
  val id: UUID,
  val email: String,
  val provider: OAuthProvider,
  val providerId: String,
  val registered: Boolean
) {
  constructor(resident: Resident) : this(
    resident.id.value,
    resident.email,
    resident.provider,
    resident.providerId,
    resident.registered
  )
}