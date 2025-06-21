package com.example.models

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ResultRow
import java.util.*

object Almacenes : UUIDTable("Almacen", columnName = "uuid") {
    val nombre = varchar("nombre", 15)
    val personal = reference("personal", Usuarios)
    val eliminado = bool("eliminado").default(false)
    val fechaModificacion = long("fecha_modificacion").clientDefault { System.currentTimeMillis() }
    val isSync = bool("is_sync").default(false)
}

data class Almacen(
    val uuid: UUID,
    val nombre: String,
    val personal: UUID,
    val eliminado: Boolean,
    val fechaModificacion: Long,
    val isSync: Boolean
) {
    companion object {
        fun fromRow(row: ResultRow): Almacen = Almacen(
            uuid = row[Almacenes.id].value,
            nombre = row[Almacenes.nombre],
            personal = row[Almacenes.personal].value,
            eliminado = row[Almacenes.eliminado],
            fechaModificacion = row[Almacenes.fechaModificacion],
            isSync = row[Almacenes.isSync]
        )
    }
}