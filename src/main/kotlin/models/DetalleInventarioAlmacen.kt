package com.example.models

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ResultRow
import java.util.*

object DetalleInventarioAlmacenes : UUIDTable("DetalleInventarioAlmacen", columnName = "uuid") {
    val almacen = reference("almacen", Almacenes)
    val prendas = reference("prendas", Prendas)
    val cantidad = integer("cantidad")
    val eliminado = bool("eliminado").default(false)
    val fechaModificacion = long("fecha_modificacion").clientDefault { System.currentTimeMillis() }
    val isSync = bool("is_sync").default(false)
}

data class DetalleInventarioAlmacen(
    val uuid: UUID,
    val almacen: UUID,
    val prendas: UUID,
    val cantidad: Int,
    val eliminado: Boolean,
    val fechaModificacion: Long,
    val isSync: Boolean
) {
    companion object {
        fun fromRow(row: ResultRow): DetalleInventarioAlmacen = DetalleInventarioAlmacen(
            uuid = row[DetalleInventarioAlmacenes.id].value,
            almacen = row[DetalleInventarioAlmacenes.almacen].value,
            prendas = row[DetalleInventarioAlmacenes.prendas].value,
            cantidad = row[DetalleInventarioAlmacenes.cantidad],
            eliminado = row[DetalleInventarioAlmacenes.eliminado],
            fechaModificacion = row[DetalleInventarioAlmacenes.fechaModificacion],
            isSync = row[DetalleInventarioAlmacenes.isSync]
        )
    }
}