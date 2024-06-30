package com.tradecrossing.domain

import com.tradecrossing.types.OAuthProvider
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