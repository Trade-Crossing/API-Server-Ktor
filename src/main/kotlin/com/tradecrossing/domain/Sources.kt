package com.tradecrossing.domain

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable


class Source(id: EntityID<Long>) : LongEntity(id) {
  companion object : LongEntityClass<Source>(Sources)

  var name by Sources.name
  var krName by Sources.krName

}

object Sources : LongIdTable("source", "id") {
  val name = varchar("name", 255)
  val krName = varchar("kr_name", 255)
}

@Serializable
data class SourceDto(val name: String, val krName: String) {
  constructor(source: Source) : this(source.name, source.krName)
}