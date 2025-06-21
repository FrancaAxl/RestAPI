package com.example.repositories

import com.example.models.DetalleInventarioAlmacen
import com.example.models.DetalleInventarioAlmacenes
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class DetalleInventarioAlmacenRepository {
    fun create(detalle: DetalleInventarioAlmacen): UUID = transaction {
        DetalleInventarioAlmacenes.insert {
            it[DetalleInventarioAlmacenes.id] = detalle.uuid
            it[almacen] = detalle.almacen
            it[prendas] = detalle.prendas
            it[cantidad] = detalle.cantidad
            it[eliminado] = detalle.eliminado
            it[fechaModificacion] = detalle.fechaModificacion
            it[isSync] = detalle.isSync
        }[DetalleInventarioAlmacenes.id].value
    }

    fun getById(uuid: UUID): DetalleInventarioAlmacen? = transaction {
        DetalleInventarioAlmacenes.select { DetalleInventarioAlmacenes.id eq uuid }
            .map { DetalleInventarioAlmacen.fromRow(it) }
            .singleOrNull()
    }

    fun update(detalle: DetalleInventarioAlmacen): Boolean = transaction {
        DetalleInventarioAlmacenes.update({ DetalleInventarioAlmacenes.id eq detalle.uuid }) {
            it[almacen] = detalle.almacen
            it[prendas] = detalle.prendas
            it[cantidad] = detalle.cantidad
            it[eliminado] = detalle.eliminado
            it[fechaModificacion] = System.currentTimeMillis()
            it[isSync] = detalle.isSync
        } > 0
    }

    fun delete(uuid: UUID): Boolean = transaction {
        DetalleInventarioAlmacenes.update({ DetalleInventarioAlmacenes.id eq uuid }) {
            it[eliminado] = true
            it[fechaModificacion] = System.currentTimeMillis()
            it[isSync] = false
        } > 0
    }

    fun getAll(): List<DetalleInventarioAlmacen> = transaction {
        DetalleInventarioAlmacenes.selectAll()
            .map { DetalleInventarioAlmacen.fromRow(it) }
    }
}