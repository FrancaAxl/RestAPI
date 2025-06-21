package com.example.routes

import com.example.models.UsuarioDTO
import com.example.services.UsuarioService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.userRoutes(usuarioService: UsuarioService) {
    route("/usuarios") {
        // Crear usuario
        post {
            val usuarioDTO = call.receive<UsuarioDTO>()
            val usuario = usuarioService.crearUsuario(usuarioDTO)
            call.respond(usuario)
        }

        // Obtener usuario por ID
        get("/{uuid}") {
            val uuid = UUID.fromString(call.parameters["uuid"])
            val usuario = usuarioService.obtenerUsuario(uuid)

            if (usuario != null) {
                call.respond(usuario)
            } else {
                call.respondText("Usuario no encontrado", status = HttpStatusCode.NotFound)
            }
        }

        // Actualizar usuario
        put("/{uuid}") {
            val uuid = UUID.fromString(call.parameters["uuid"])
            val usuarioDTO = call.receive<UsuarioDTO>()
            val usuarioActualizado = usuarioService.actualizarUsuario(uuid, usuarioDTO)

            if (usuarioActualizado != null) {
                call.respond(usuarioActualizado)
            } else {
                call.respondText("Usuario no encontrado", status = HttpStatusCode.NotFound)
            }
        }

        // Eliminar usuario (borrado lógico)
        delete("/{uuid}") {
            val uuid = UUID.fromString(call.parameters["uuid"])
            if (usuarioService.eliminarUsuario(uuid)) {
                call.respondText("Usuario eliminado correctamente")
            } else {
                call.respondText("Usuario no encontrado", status = HttpStatusCode.NotFound)
            }
        }

        // Listar todos los usuarios
        get {
            val usuarios = usuarioService.listarUsuarios()
            call.respond(usuarios)
        }
    }

    // Autenticación
    post("/login") {
        val credenciales = call.receive<Map<String, String>>()
        val username = credenciales["username"] ?: ""
        val password = credenciales["password"] ?: ""

        val usuario = usuarioService.autenticarUsuario(username, password)
        if (usuario != null) {
            call.respond(mapOf("token" to "generar-token-jwt-aqui", "usuario" to usuario))
        } else {
            call.respondText("Credenciales inválidas", status = HttpStatusCode.Unauthorized)
        }
    }
}