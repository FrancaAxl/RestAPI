package com.example.repositories

import com.example.models.Usuario
import com.example.models.Usuarios
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class UsuarioRepository {
    fun create(usuario: Usuario): UUID = transaction {
        Usuarios.insert {
            it[Usuarios.id] = usuario.uuid
            it[nombre] = usuario.nombre
            it[user] = usuario.user
            it[password] = usuario.password
            it[cargo] = usuario.cargo
            it[eliminado] = usuario.eliminado
            it[fechaModificacion] = usuario.fechaModificacion
            it[isSync] = usuario.isSync
        }[Usuarios.id].value
    }

    fun getById(uuid: UUID): Usuario? = transaction {
        Usuarios.select { Usuarios.id eq uuid }
            .map { Usuario.fromRow(it) }
            .singleOrNull()
    }

    fun getByUsername(username: String): Usuario? = transaction {
        Usuarios.select { Usuarios.user eq username }
            .map { Usuario.fromRow(it) }
            .singleOrNull()
    }

    fun update(usuario: Usuario): Boolean = transaction {
        Usuarios.update({ Usuarios.id eq usuario.uuid }) {
            it[nombre] = usuario.nombre
            it[user] = usuario.user
            it[password] = usuario.password
            it[cargo] = usuario.cargo
            it[eliminado] = usuario.eliminado
            it[fechaModificacion] = System.currentTimeMillis()
            it[isSync] = usuario.isSync
        } > 0
    }

    fun delete(uuid: UUID): Boolean = transaction {
        Usuarios.update({ Usuarios.id eq uuid }) {
            it[eliminado] = true
            it[fechaModificacion] = System.currentTimeMillis()
            it[isSync] = false
        } > 0
    }

    fun getAll(): List<Usuario> = transaction {
        Usuarios.selectAll()
            .map { Usuario.fromRow(it) }
    }
}