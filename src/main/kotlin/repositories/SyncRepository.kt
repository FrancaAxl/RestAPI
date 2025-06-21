package com.example.repositories


import com.example.models.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.*
import java.util.UUID

abstract class SyncRepository {
    abstract fun getUnsyncedPrendas(): List<Prenda>
    abstract fun getUnsyncedUsuarios(): List<Usuario>
    abstract fun markAsSynced(uuid: UUID, tableName: String)
}