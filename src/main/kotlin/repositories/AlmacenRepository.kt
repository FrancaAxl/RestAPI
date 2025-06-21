package com.example.repositories

import com.example.models.Almacen
import com.example.models.Almacenes
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class AlmacenRepository {
    fun create(almacen: Almacen): UUID = transaction {
        Almacenes.insert {
            it[Almacenes.id] = almacen.uuid
            it[nombre] = almacen.nombre
            it[personal] = almacen.personal
            it[eliminado] = almacen.eliminado
            it[fechaModificacion] = almacen.fechaModificacion
            it[isSync] = almacen.isSync
        }[Almacenes.id].value
    }

    fun getById(uuid: UUID): Almacen? = transaction {
        Almacenes.select { Almacenes.id eq uuid }
            .map { Almacen.fromRow(it) }
            .singleOrNull()
    }

    fun update(almacen: Almacen): Boolean = transaction {
        Almacenes.update({ Almacenes.id eq almacen.uuid }) {
            it[nombre] = almacen.nombre
            it[personal] = almacen.personal
            it[eliminado] = almacen.eliminado
            it[fechaModificacion] = System.currentTimeMillis()
            it[isSync] = almacen.isSync
        } > 0
    }

    fun delete(uuid: UUID): Boolean = transaction {
        Almacenes.update({ Almacenes.id eq uuid }) {
            it[eliminado] = true
            it[fechaModificacion] = System.currentTimeMillis()
            it[isSync] = false
        } > 0
    }

    fun getAll(): List<Almacen> = transaction {
        Almacenes.selectAll()
            .map { Almacen.fromRow(it) }
    }
}