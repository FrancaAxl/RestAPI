package com.example.repositories

import com.example.models.Cargo
import com.example.models.Cargos
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class CargoRepository {
    fun create(cargo: Cargo): UUID = transaction {
        Cargos.insert {
            it[Cargos.id] = cargo.uuid
            it[nombreCargo] = cargo.nombreCargo
            it[nivelCargo] = cargo.nivelCargo
            it[eliminado] = cargo.eliminado
            it[fechaModificacion] = cargo.fechaModificacion
            it[isSync] = cargo.isSync
        }[Cargos.id].value
    }

    fun getById(uuid: UUID): Cargo? = transaction {
        Cargos.select { Cargos.id eq uuid }
            .map { Cargo.fromRow(it) }
            .singleOrNull()
    }

    fun update(cargo: Cargo): Boolean = transaction {
        Cargos.update({ Cargos.id eq cargo.uuid }) {
            it[nombreCargo] = cargo.nombreCargo
            it[nivelCargo] = cargo.nivelCargo
            it[eliminado] = cargo.eliminado
            it[fechaModificacion] = System.currentTimeMillis()
            it[isSync] = cargo.isSync
        } > 0
    }

    fun delete(uuid: UUID): Boolean = transaction {
        Cargos.update({ Cargos.id eq uuid }) {
            it[eliminado] = true
            it[fechaModificacion] = System.currentTimeMillis()
            it[isSync] = false
        } > 0
    }

    fun getAll(): List<Cargo> = transaction {
        Cargos.selectAll()
            .map { Cargo.fromRow(it) }
    }
}