package com.example.services

import com.example.models.Usuario
import com.example.repositories.UsuarioRepository
import java.util.*

class UsuarioService(private val repository: UsuarioRepository) {
    fun createUsuario(nombre: String, user: String, password: String, cargo: UUID): Usuario {
        val usuario = Usuario(
            uuid = UUID.randomUUID(),
            nombre = nombre,
            user = user,
            password = password,
            cargo = cargo,
            eliminado = false,
            fechaModificacion = System.currentTimeMillis(),
            isSync = false
        )
        repository.create(usuario)
        return usuario
    }

    fun getUsuario(uuid: UUID): Usuario? {
        return repository.getById(uuid)
    }

    fun updateUsuario(uuid: UUID, nombre: String, user: String, cargo: UUID): Usuario? {
        val usuario = repository.getById(uuid) ?: return null
        val updatedUsuario = usuario.copy(
            nombre = nombre,
            user = user,
            cargo = cargo,
            fechaModificacion = System.currentTimeMillis()
        )
        return if (repository.update(updatedUsuario)) updatedUsuario else null
    }

    fun deleteUsuario(uuid: UUID): Boolean {
        return repository.delete(uuid)
    }

    fun listUsuarios(): List<Usuario> {
        return repository.getAll()
    }

    fun authenticate(username: String, password: String): Usuario? {
        val usuario = repository.getByUsername(username)
        return if (usuario != null && usuario.password == password) usuario else null
    }
}