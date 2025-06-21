package com.example.repositories

import com.example.models.Prenda
import com.example.models.Prendas
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class PrendaRepository {
    fun create(prenda: Prenda): UUID = transaction {
        // Usar Prendas.id.value para obtener el UUID generado
        Prendas.insert {
            it[id] = prenda.uuid
            it[codigoBarras] = prenda.codigoBarras
            it[nombreProducto] = prenda.nombreProducto
            it[talla] = prenda.talla
            it[color] = prenda.color
            it[eliminado] = prenda.eliminado
            it[fechaModificacion] = prenda.fechaModificacion
            it[isSync] = prenda.isSync
        }.get(Prendas.id).value
    }

    fun getById(uuid: UUID): Prenda? = transaction {
        Prendas.select { Prendas.id eq uuid }
            .map { Prenda.fromRow(it) }
            .singleOrNull()
    }

    fun getByCodigoBarras(codigo: String): Prenda? = transaction {
        Prendas.select { Prendas.codigoBarras eq codigo }
            .map { Prenda.fromRow(it) }
            .singleOrNull()
    }

    fun update(prenda: Prenda): Boolean = transaction {
        Prendas.update({ Prendas.id eq prenda.uuid }) {
            it[codigoBarras] = prenda.codigoBarras
            it[nombreProducto] = prenda.nombreProducto
            it[talla] = prenda.talla
            it[color] = prenda.color
            it[eliminado] = prenda.eliminado
            it[fechaModificacion] = System.currentTimeMillis()
            it[isSync] = prenda.isSync
        } > 0
    }

    fun delete(uuid: UUID): Boolean = transaction {
        Prendas.update({ Prendas.id eq uuid }) {
            it[eliminado] = true
            it[fechaModificacion] = System.currentTimeMillis()
            it[isSync] = false
        } > 0
    }

    fun getAll(): List<Prenda> = transaction {
        Prendas.selectAll()
            .map { Prenda.fromRow(it) }
    }

    fun getUnsynced(): List<Prenda> = transaction {
        Prendas.select { Prendas.isSync eq false }
            .map { Prenda.fromRow(it) }
    }
}