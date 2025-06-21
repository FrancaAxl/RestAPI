package com.example.models

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ResultRow
import java.util.*

object PackingLists : UUIDTable("PackingList", columnName = "uuid") {
    val folio = varchar("folio", 8).uniqueIndex()
    val envia = text("envia")
    val recibe = text("recibe")
    val transporte = varchar("transporte", 20)
    val codigoContenedor = varchar("codigoContenedor", 20)
    val eliminado = bool("eliminado").default(false)
    val fechaModificacion = long("fecha_modificacion").clientDefault { System.currentTimeMillis() }
    val isSync = bool("is_sync").default(false)
}

data class PackingList(
    val uuid: UUID,
    val folio: String,
    val envia: String,
    val recibe: String,
    val transporte: String,
    val codigoContenedor: String,
    val eliminado: Boolean,
    val fechaModificacion: Long,
    val isSync: Boolean
) {
    companion object {
        fun fromRow(row: ResultRow): PackingList = PackingList(
            uuid = row[PackingLists.id].value,
            folio = row[PackingLists.folio],
            envia = row[PackingLists.envia],
            recibe = row[PackingLists.recibe],
            transporte = row[PackingLists.transporte],
            codigoContenedor = row[PackingLists.codigoContenedor],
            eliminado = row[PackingLists.eliminado],
            fechaModificacion = row[PackingLists.fechaModificacion],
            isSync = row[PackingLists.isSync]
        )
    }
}