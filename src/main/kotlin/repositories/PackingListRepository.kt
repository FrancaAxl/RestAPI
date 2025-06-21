package com.example.repositories

import com.example.models.PackingList
import com.example.models.PackingLists
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class PackingListRepository {
    fun create(packingList: PackingList): UUID = transaction {
        PackingLists.insert {
          it[PackingLists.id] = packingList.uuid
            it[folio] = packingList.folio
            it[envia] = packingList.envia
            it[recibe] = packingList.recibe
            it[transporte] = packingList.transporte
            it[codigoContenedor] = packingList.codigoContenedor
            it[eliminado] = packingList.eliminado
            it[fechaModificacion] = packingList.fechaModificacion
            it[isSync] = packingList.isSync
        }[PackingLists.id].value
    }

    fun getById(uuid: UUID): PackingList? = transaction {
        PackingLists.select { PackingLists.id eq uuid }
            .map { PackingList.fromRow(it) }
            .singleOrNull()
    }

    fun update(packingList: PackingList): Boolean = transaction {
        PackingLists.update({ PackingLists.id eq packingList.uuid }) {
            it[folio] = packingList.folio
            it[envia] = packingList.envia
            it[recibe] = packingList.recibe
            it[transporte] = packingList.transporte
            it[codigoContenedor] = packingList.codigoContenedor
            it[eliminado] = packingList.eliminado
            it[fechaModificacion] = System.currentTimeMillis()
            it[isSync] = packingList.isSync
        } > 0
    }

    fun delete(uuid: UUID): Boolean = transaction {
        PackingLists.update({ PackingLists.id eq uuid }) {
            it[eliminado] = true
            it[fechaModificacion] = System.currentTimeMillis()
            it[isSync] = false
        } > 0
    }

    fun getAll(): List<PackingList> = transaction {
        PackingLists.selectAll()
            .map { PackingList.fromRow(it) }
    }
}