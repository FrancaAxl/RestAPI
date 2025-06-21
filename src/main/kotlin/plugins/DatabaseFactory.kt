package com.example.plugins

import com.example.models.*
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

data class DatabaseConfig(
    val url: String,
    val user: String,
    val password: String,
    val poolSize: Int = 10,
    val showSql: Boolean = false
)

object DatabaseFactory {
    private var dataSource: HikariDataSource? = null

    fun init(config: DatabaseConfig) {
        val hikariConfig = HikariConfig().apply {
            jdbcUrl = config.url
            username = config.user
            this.password = config.password
            driverClassName = "com.mysql.cj.jdbc.Driver"
            maximumPoolSize = config.poolSize
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"

            // Optimizaciones para MySQL
            addDataSourceProperty("cachePrepStmts", "true")
            addDataSourceProperty("prepStmtCacheSize", "250")
            addDataSourceProperty("prepStmtCacheSqlLimit", "2048")
            addDataSourceProperty("useServerPrepStmts", "true")
        }

        dataSource = HikariDataSource(hikariConfig)
        Database.connect(dataSource!!)

        // Crear tablas si no existen
        transaction {
            if (config.showSql) {
                addLogger(StdOutSqlLogger)
            }

            SchemaUtils.createMissingTablesAndColumns(
                Cargos,
                Usuarios,
                Almacenes,
                Prendas,
                PackingLists,
                DetallePackingLists,
                DetalleInventarioAlmacenes
            )
        }
    }

    fun close() {
        dataSource?.close()
        dataSource = null
    }
}