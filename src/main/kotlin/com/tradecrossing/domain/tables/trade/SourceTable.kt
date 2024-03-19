package com.tradecrossing.domain.tables.trade

import org.jetbrains.exposed.dao.id.LongIdTable

object SourceTable : LongIdTable("source", "id") {
  val name = varchar("name", 255)
  val krName = varchar("kr_name", 255)
}