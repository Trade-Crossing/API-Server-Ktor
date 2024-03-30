package com.tradecrossing.domain.tables.resident

import com.tradecrossing.types.OAuthProvider
import org.jetbrains.exposed.dao.id.UUIDTable

object ResidentTable : UUIDTable("resident", "id") {
  val email = varchar("email", 255).uniqueIndex("resident_email_key")
  val provider = enumerationByName<OAuthProvider>("provider", 20)
  val providerId = varchar("provider_id", 255).uniqueIndex("resident_provider_id_key")
  val registered = bool("registered").default(false)
  val islandCode = varchar("island_code", 255).nullable()
}