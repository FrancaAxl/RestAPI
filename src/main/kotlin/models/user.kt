package com.example.models

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime
import java.util.*

object Usuarios : UUIDTable("Usuario", columnName = "uuid") {
    val nombre = varchar("nombre", 40)
    val user = varchar("user", 25).uniqueIndex()
    val password = varchar("password", 255) // Recomiendo hashear las contraseñas
    val cargo = reference("cargo", Cargos)
    val eliminado = bool("eliminado").default(false)
    val fechaModificacion = long("fecha_modificacion").clientDefault { System.currentTimeMillis() }
    val isSync = bool("is_sync").default(false)
}

data class Usuario(
    val uuid: UUID,
    val nombre: String,
    val user: String,
    val password: String,
    val cargo: UUID,
    val eliminado: Boolean,
    val fechaModificacion: Long,
    val isSync: Boolean
) {
    companion object {
        fun fromRow(row: ResultRow): Usuario = Usuario(
            uuid = row[Usuarios.id].value,
            nombre = row[Usuarios.nombre],
            user = row[Usuarios.user],
            password = row[Usuarios.password],
            cargo = row[Usuarios.cargo].value,
            eliminado = row[Usuarios.eliminado],
            fechaModificacion = row[Usuarios.fechaModificacion],
            isSync = row[Usuarios.isSync]
        )
    }
}

data class UsuarioDTO(
    val uuid: String? = null,
    val nombre: String,
    val user: String,
    val password: String? = null,
    val cargo: String,
    val eliminado: Boolean? = false,
    val fechaModificacion: Long? = null,
    val isSync: Boolean? = false
)