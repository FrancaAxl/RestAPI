package com.example.models

import org.jetbrains.exposed.sql.Table

object Prendas : Table("prenda") {
    val id = integer("id").autoIncrement()
    val codigoBarras = char("codigo_barras", 13)
    val nombre = varchar("nombre", 20)
    val talla = char("talla", 2)
    val color = varchar("color", 15)
    val eliminado = bool("eliminado").default(false)

    override val primaryKey = PrimaryKey(id)
}