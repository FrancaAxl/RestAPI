package com.example.models

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ResultRow
import java.util.*

object Cargos : UUIDTable("Cargo", columnName = "uuid") {
    val nombreCargo = varchar("nombreCargo", 15)
    val nivelCargo = integer("nivelCargo")
    val eliminado = bool("eliminado").default(false)
    val fechaModificacion = long("fecha_modificacion").clientDefault { System.currentTimeMillis() }
    val isSync = bool("is_sync").default(false)
}

data class Cargo(
    val uuid: UUID,
    val nombreCargo: String,
    val nivelCargo: Int,
    val eliminado: Boolean,
    val fechaModificacion: Long,
    val isSync: Boolean
) {
    companion object {
        fun fromRow(row: ResultRow): Cargo = Cargo(
            uuid = row[Cargos.id].value,
            nombreCargo = row[Cargos.nombreCargo],
            nivelCargo = row[Cargos.nivelCargo],
            eliminado = row[Cargos.eliminado],
            fechaModificacion = row[Cargos.fechaModificacion],
            isSync = row[Cargos.isSync]
        )
    }
}