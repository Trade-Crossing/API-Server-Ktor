package com.tradecrossing.domain.entity

import com.tradecrossing.domain.tables.ResidentTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class ResidentEntity(id: EntityID<UUID>) : UUIDEntity(id) {

  companion object : UUIDEntityClass<ResidentEntity>(ResidentTable)

  var email by ResidentTable.email
  var provider by ResidentTable.provider
  var providerId by ResidentTable.providerId
  var registered by ResidentTable.registered

}