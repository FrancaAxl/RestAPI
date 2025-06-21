package com.example.models

import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object Prendas : UUIDTable("Prenda", columnName = "uuid") {
    val codigoBarras = varchar("codigoBarras", 13).uniqueIndex()
    val nombreProducto = varchar("nombreProducto", 50)
    val talla = varchar("talla", 2)
    val color = varchar("color", 30)
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
)