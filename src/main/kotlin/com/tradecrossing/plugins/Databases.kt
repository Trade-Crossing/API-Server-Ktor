package com.tradecrossing.plugins

import io.ktor.server.config.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object DatabaseFactory {

  const val driver = "org.postgresql.Driver"
  private lateinit var database: Database

  fun init(config: ApplicationConfig) {
    val mode = System.getenv("MODE")

    val url: String?
    val username: String?
    val password: String?

    when (mode) {
      "local" -> {
        url = "jdbc:postgresql://postgresql.orb.local:5432/trade_crossing"
        username = "postgres"
        password = "postgres"
        database = Database.connect(url, driver, username, password)
      }

      "dev" -> {
        url = config.propertyOrNull("database.url")?.getString()
        username = config.propertyOrNull("database.username")?.getString()
        password = config.propertyOrNull("database.password")?.getString()
        database = Database.connect(url!!, driver, username!!, password!!)
      }

      "prod" -> {
        // Initialize production database
      }

      else -> throw IllegalArgumentException("Unknown mode: $mode")
    }
  }

  suspend fun <T> dbQuery(block: Transaction.() -> T): T = newSuspendedTransaction { block() }
}