package com.example.services

import com.example.models.Usuario
import com.example.models.UsuarioDTO
import com.example.repositories.UsuarioRepository
import java.util.*

class UsuarioService(private val usuarioRepository: UsuarioRepository) {
    fun crearUsuario(usuarioDTO: UsuarioDTO): Usuario {
        val usuario = Usuario(
            uuid = UUID.randomUUID(),
            nombre = usuarioDTO.nombre,
            user = usuarioDTO.user,
            password = usuarioDTO.password ?: throw IllegalArgumentException("Password es requerido"),
            cargo = UUID.fromString(usuarioDTO.cargo),
            eliminado = false,
            fechaModificacion = System.currentTimeMillis(),
            isSync = false
        )

        usuarioRepository.crearUsuario(usuario)
        return usuario
    }

    fun obtenerUsuario(uuid: UUID): Usuario? {
        return usuarioRepository.obtenerPorId(uuid)
    }

    fun actualizarUsuario(uuid: UUID, usuarioDTO: UsuarioDTO): Usuario? {
        val usuarioExistente = usuarioRepository.obtenerPorId(uuid) ?: return null

        val usuarioActualizado = usuarioExistente.copy(
            nombre = usuarioDTO.nombre,
            user = usuarioDTO.user,
            cargo = UUID.fromString(usuarioDTO.cargo),
            fechaModificacion = System.currentTimeMillis(),
            isSync = false
        )

        return if (usuarioRepository.actualizarUsuario(usuarioActualizado)) {
            usuarioActualizado
        } else {
            null
        }
    }

    fun eliminarUsuario(uuid: UUID): Boolean {
        return usuarioRepository.eliminarUsuario(uuid)
    }

    fun listarUsuarios(): List<Usuario> {
        return usuarioRepository.listarUsuarios()
    }

    fun autenticarUsuario(username: String, password: String): Usuario? {
        val usuario = usuarioRepository.obtenerPorUsername(username)
        return if (usuario != null && usuario.password == password) {
            usuario
        } else {
            null
        }
    }
}