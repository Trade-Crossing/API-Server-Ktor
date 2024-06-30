package com.tradecrossing.domain

import com.tradecrossing.types.UUIDSerializer
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import java.util.*

class ResidentInfo(id: EntityID<UUID>) : Entity<UUID>(id) {
  companion object : EntityClass<UUID, ResidentInfo>(ResidentInfos)

  var introduction by ResidentInfos.introduction
  var islandName by ResidentInfos.islandName
  var profilePic by ResidentInfos.profilePic
  var username by ResidentInfos.username
  val resident by Resident referencedOn ResidentInfos.id
  var islandCode by ResidentInfos.islandCode

  override fun toString(): String {
    return "ResidentInfoEntity(id=$id, introduction=$introduction, islandName=$islandName, profilePic=$profilePic, username=$username)"
  }

}

object ResidentInfos : IdTable<UUID>("resident_info") {

  override val id: Column<EntityID<UUID>> = uuid("id").entityId().references(Residents.id)
  val introduction = text("introduction")
  val islandName = varchar("island_name", 255)
  val profilePic = text("profile_pic").nullable()
  val username = varchar("username", 255)
  val islandCode = varchar("island_code", 255).nullable()

  override val primaryKey = PrimaryKey(id)
}

@Serializable
data class ResidentInfoDto(
  @Serializable(with = UUIDSerializer::class)
  val id: UUID,
  val username: String,
  val islandName: String,
  val introduction: String,
  val profilePic: String?,
  val islandCode: String?
) {
  constructor(residentInfo: ResidentInfo) : this(
    residentInfo.id.value,
    residentInfo.username,
    residentInfo.islandName,
    residentInfo.introduction,
    residentInfo.profilePic,
    residentInfo.islandCode
  )

}