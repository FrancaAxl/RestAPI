package com.example.models

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ResultRow
import java.util.*

object DetallePackingLists : UUIDTable("DetallePackingList", columnName = "uuid") {
    val folio = reference("folio", PackingLists)
    val prendas = reference("prendas", Prendas)
    val cantidad = integer("cantidad")
    val eliminado = bool("eliminado").default(false)
    val fechaModificacion = long("fecha_modificacion").clientDefault { System.currentTimeMillis() }
    val isSync = bool("is_sync").default(false)
}

data class DetallePackingList(
    val uuid: UUID,
    val folio: UUID,
    val prendas: UUID,
    val cantidad: Int,
    val eliminado: Boolean,
    val fechaModificacion: Long,
    val isSync: Boolean
) {
    companion object {
        fun fromRow(row: ResultRow): DetallePackingList = DetallePackingList(
            uuid = row[DetallePackingLists.id].value,
            folio = row[DetallePackingLists.folio].value,
            prendas = row[DetallePackingLists.prendas].value,
            cantidad = row[DetallePackingLists.cantidad],
            eliminado = row[DetallePackingLists.eliminado],
            fechaModificacion = row[DetallePackingLists.fechaModificacion],
            isSync = row[DetallePackingLists.isSync]
        )
    }
}