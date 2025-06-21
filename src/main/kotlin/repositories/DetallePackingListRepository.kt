package com.example.repositories

import com.example.models.DetallePackingList
import com.example.models.DetallePackingLists
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class DetallePackingListRepository {
    fun create(detalle: DetallePackingList): UUID = transaction {
        DetallePackingLists.insert {
            it[DetallePackingLists.id] = detalle.uuid
            it[folio] = detalle.folio
            it[prendas] = detalle.prendas
            it[cantidad] = detalle.cantidad
            it[eliminado] = detalle.eliminado
            it[fechaModificacion] = detalle.fechaModificacion
            it[isSync] = detalle.isSync
        }[DetallePackingLists.id].value
    }

    fun getById(uuid: UUID): DetallePackingList? = transaction {
        DetallePackingLists.select { DetallePackingLists.id eq uuid }
            .map { DetallePackingList.fromRow(it) }
            .singleOrNull()
    }

    fun update(detalle: DetallePackingList): Boolean = transaction {
        DetallePackingLists.update({ DetallePackingLists.id eq detalle.uuid }) {
            it[folio] = detalle.folio
            it[prendas] = detalle.prendas
            it[cantidad] = detalle.cantidad
            it[eliminado] = detalle.eliminado
            it[fechaModificacion] = System.currentTimeMillis()
            it[isSync] = detalle.isSync
        } > 0
    }

    fun delete(uuid: UUID): Boolean = transaction {
        DetallePackingLists.update({ DetallePackingLists.id eq uuid }) {
            it[eliminado] = true
            it[fechaModificacion] = System.currentTimeMillis()
            it[isSync] = false
        } > 0
    }

    fun getAll(): List<DetallePackingList> = transaction {
        DetallePackingLists.selectAll()
            .map { DetallePackingList.fromRow(it) }
    }
}