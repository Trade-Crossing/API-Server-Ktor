package com.tradecrossing.domain.entity.resident

import com.tradecrossing.domain.tables.resident.ResidentInfoTable
import com.tradecrossing.domain.tables.resident.ResidentTable
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class ResidentInfoEntity(id: EntityID<UUID>) : Entity<UUID>(id) {
  companion object : EntityClass<UUID, ResidentInfoEntity>(ResidentInfoTable)

  var defaultProfile by ResidentInfoTable.defaultProfile
  var introduction by ResidentInfoTable.introduction
  var islandName by ResidentInfoTable.islandName
  var profilePic by ResidentInfoTable.profilePic
  var username by ResidentInfoTable.username
  val resident by ResidentEntity referencedOn ResidentInfoTable.id
  var islandCode by ResidentTable.islandCode

  override fun toString(): String {
    return "ResidentInfoEntity(id=$id, defaultProfile=$defaultProfile, introduction=$introduction, islandName=$islandName, profilePic=$profilePic, username=$username)"
  }
}