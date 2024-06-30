package com.tradecrossing.system.plugins

import com.tradecrossing.domain.*
import io.ktor.server.config.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

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
        url = "jdbc:postgresql://localhost:5432/trade_crossing"
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

    transaction {
      addLogger(StdOutSqlLogger)
      SchemaUtils.createMissingTablesAndColumns(
        ResidentTable,
        ResidentInfoTable,
        ItemTradeTable,
        ItemCategoryTable,
        SourceTable,
        VillagerTradeTable,
        VillagerCategoryTable,
        Reports
      )
    }
  }

  suspend fun <T> dbQuery(block: Transaction.() -> T): T = newSuspendedTransaction(Dispatchers.IO, database) { block() }
}