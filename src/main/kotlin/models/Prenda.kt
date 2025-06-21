package com.example.models

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ResultRow
import java.util.UUID

// Tabla usando UUIDTable como en Usuarios
object Prendas : UUIDTable(name = "Prendas", columnName = "uuid") {
    val codigoBarras = varchar("codigo_barras", 50)
    val nombreProducto = varchar("nombre_producto", 100)
    val talla = varchar("talla", 10)
    val color = varchar("color", 20)
    val eliminado = bool("eliminado").default(false)
    val fechaModificacion = long("fecha_modificacion").clientDefault { System.currentTimeMillis() }
    val isSync = bool("is_sync").default(false)
}

data class Prenda(
    val uuid: UUID,
    val codigoBarras: String,
    val nombreProducto: String,
    val talla: String,
    val color: String,
    val eliminado: Boolean,
    val fechaModificacion: Long,
    val isSync: Boolean
) {
    companion object {
        fun fromRow(row: ResultRow): Prenda = Prenda(
            // Usar .value como en el modelo Usuario
            uuid = row[Prendas.id].value,
            codigoBarras = row[Prendas.codigoBarras],
            nombreProducto = row[Prendas.nombreProducto],
            talla = row[Prendas.talla],
            color = row[Prendas.color],
            eliminado = row[Prendas.eliminado],
            fechaModificacion = row[Prendas.fechaModificacion],
            isSync = row[Prendas.isSync]
        )
    }
}