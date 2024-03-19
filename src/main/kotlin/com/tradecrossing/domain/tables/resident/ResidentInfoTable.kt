package com.tradecrossing.domain.tables.resident

import com.tradecrossing.types.DefaultProfile
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import java.util.*

object ResidentInfoTable : IdTable<UUID>("resident_info") {

  override val id: Column<EntityID<UUID>> = uuid("id").entityId().references(ResidentTable.id)
  val defaultProfile = enumerationByName<DefaultProfile>("default_profile", 10).nullable()
  val introduction = text("introduction")
  val islandName = varchar("island_name", 255)
  val profilePic = text("profile_pic").nullable()
  val username = varchar("username", 255)
}