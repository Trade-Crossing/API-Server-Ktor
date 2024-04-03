package com.tradecrossing.domain.entity.resident

import com.tradecrossing.domain.tables.resident.ResidentInfoTable
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class ResidentInfoEntity(id: EntityID<UUID>) : Entity<UUID>(id) {
  companion object : EntityClass<UUID, ResidentInfoEntity>(ResidentInfoTable)

  var introduction by ResidentInfoTable.introduction
  var islandName by ResidentInfoTable.islandName
  var profilePic by ResidentInfoTable.profilePic
  var username by ResidentInfoTable.username
  val resident by ResidentEntity referencedOn ResidentInfoTable.id
  var islandCode by ResidentInfoTable.islandCode

  override fun toString(): String {
    return "ResidentInfoEntity(id=$id, introduction=$introduction, islandName=$islandName, profilePic=$profilePic, username=$username)"
  }
}