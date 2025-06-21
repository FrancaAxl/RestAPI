package com.example.routes

import com.example.services.UsuarioService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.userRoutes(service: UsuarioService) {
    route("/usuarios") {
        // Crear nuevo usuario
        post {
            val params = call.receiveParameters()
            val nombre = params["nombre"] ?: return@post call.respondText(
                "Nombre requerido",
                status = HttpStatusCode.BadRequest
            )
            val user = params["user"] ?: return@post call.respondText(
                "Usuario requerido",
                status = HttpStatusCode.BadRequest
            )
            val password = params["password"] ?: return@post call.respondText(
                "Contraseña requerida",
                status = HttpStatusCode.BadRequest
            )
            val cargo = UUID.fromString(params["cargo"] ?: return@post call.respondText(
                "Cargo requerido",
                status = HttpStatusCode.BadRequest
            ))

            val usuario = service.createUsuario(nombre, user, password, cargo)
            call.respond(usuario)
        }

        // Obtener usuario por ID
        get("/{id}") {
            val id = UUID.fromString(call.parameters["id"] ?: return@get call.respondText(
                "ID requerido",
                status = HttpStatusCode.BadRequest
            ))

            val usuario = service.getUsuario(id)
            if (usuario != null) {
                call.respond(usuario)
            } else {
                call.respondText("Usuario no encontrado", status = HttpStatusCode.NotFound)
            }
        }

        // Actualizar usuario
        put("/{id}") {
            val id = UUID.fromString(call.parameters["id"] ?: return@put call.respondText(
                "ID requerido",
                status = HttpStatusCode.BadRequest
            ))

            val params = call.receiveParameters()
            val nombre = params["nombre"] ?: return@put call.respondText(
                "Nombre requerido",
                status = HttpStatusCode.BadRequest
            )
            val user = params["user"] ?: return@put call.respondText(
                "Usuario requerido",
                status = HttpStatusCode.BadRequest
            )
            val cargo = UUID.fromString(params["cargo"] ?: return@put call.respondText(
                "Cargo requerido",
                status = HttpStatusCode.BadRequest
            ))

            val usuario = service.updateUsuario(id, nombre, user, cargo)
            if (usuario != null) {
                call.respond(usuario)
            } else {
                call.respondText("Usuario no encontrado", status = HttpStatusCode.NotFound)
            }
        }

        // Eliminar usuario
        delete("/{id}") {
            val id = UUID.fromString(call.parameters["id"] ?: return@delete call.respondText(
                "ID requerido",
                status = HttpStatusCode.BadRequest
            ))

            if (service.deleteUsuario(id)) {
                call.respondText("Usuario eliminado correctamente")
            } else {
                call.respondText("Usuario no encontrado", status = HttpStatusCode.NotFound)
            }
        }

        // Listar todos los usuarios
        get {
            val usuarios = service.listUsuarios()
            call.respond(usuarios)
        }
    }

    // Autenticación
    post("/login") {
        val params = call.receiveParameters()
        val user = params["user"] ?: return@post call.respondText(
            "Usuario requerido",
            status = HttpStatusCode.BadRequest
        )
        val password = params["password"] ?: return@post call.respondText(
            "Contraseña requerida",
            status = HttpStatusCode.BadRequest
        )

        val usuario = service.authenticate(user, password)
        if (usuario != null) {
            call.respond(mapOf(
                "status" to "success",
                "usuario" to usuario,
                "token" to "generar-token-jwt-aqui"
            ))
        } else {
            call.respondText("Credenciales inválidas", status = HttpStatusCode.Unauthorized)
        }
    }
}