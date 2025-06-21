package com.example.repositories


import com.example.models.Usuario
import com.example.models.Usuarios
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class UsuarioRepository {
    fun crearUsuario(usuario: Usuario): UUID = transaction {
        Usuarios.insert {
            it[id] = usuario.uuid
            it[nombre] = usuario.nombre
            it[user] = usuario.user
            it[password] = usuario.password
            it[cargo] = UUID.fromString(usuario.cargo.toString())
            it[eliminado] = usuario.eliminado
            it[fechaModificacion] = usuario.fechaModificacion
            it[isSync] = usuario.isSync
        }[Usuarios.id].value
    }

    fun obtenerPorId(uuid: UUID): Usuario? = transaction {
        Usuarios.select { Usuarios.id eq uuid }
            .map { Usuario.fromRow(it) }
            .singleOrNull()
    }

    fun obtenerPorUsername(username: String): Usuario? = transaction {
        Usuarios.select { Usuarios.user eq username }
            .map { Usuario.fromRow(it) }
            .singleOrNull()
    }

    fun actualizarUsuario(usuario: Usuario): Boolean = transaction {
        Usuarios.update({ Usuarios.id eq usuario.uuid }) {
            it[nombre] = usuario.nombre
            it[user] = usuario.user
            it[password] = usuario.password
            it[cargo] = UUID.fromString(usuario.cargo.toString())
            it[eliminado] = usuario.eliminado
            it[fechaModificacion] = System.currentTimeMillis()
            it[isSync] = usuario.isSync
        } > 0
    }

    fun eliminarUsuario(uuid: UUID): Boolean = transaction {
        Usuarios.update({ Usuarios.id eq uuid }) {
            it[eliminado] = true
            it[fechaModificacion] = System.currentTimeMillis()
            it[isSync] = false
        } > 0
    }

    fun listarUsuarios(): List<Usuario> = transaction {
        Usuarios.selectAll()
            .map { Usuario.fromRow(it) }
    }
}