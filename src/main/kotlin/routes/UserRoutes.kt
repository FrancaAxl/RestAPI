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
            try {
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
                val cargoString = params["cargo"] ?: return@post call.respondText(
                    "Cargo requerido",
                    status = HttpStatusCode.BadRequest
                )

                val cargo = try {
                    UUID.fromString(cargoString)
                } catch (e: IllegalArgumentException) {
                    return@post call.respondText(
                        "Formato de cargo inválido",
                        status = HttpStatusCode.BadRequest
                    )
                }

                val usuario = service.createUsuario(nombre, user, password, cargo)
                call.respond(HttpStatusCode.Created, usuario)
            } catch (e: Exception) {
                call.respondText(
                    "Error interno del servidor: ${e.message}",
                    status = HttpStatusCode.InternalServerError
                )
            }
        }

        // Listar todos los usuarios (movido antes de get("/{id}") para evitar conflictos)
        get {
            try {
                val usuarios = service.listUsuarios()
                call.respond(usuarios)
            } catch (e: Exception) {
                call.respondText(
                    "Error al obtener usuarios: ${e.message}",
                    status = HttpStatusCode.InternalServerError
                )
            }
        }

        // Obtener usuario por ID
        get("/{id}") {
            try {
                val idString = call.parameters["id"] ?: return@get call.respondText(
                    "ID requerido",
                    status = HttpStatusCode.BadRequest
                )

                val id = try {
                    UUID.fromString(idString)
                } catch (e: IllegalArgumentException) {
                    return@get call.respondText(
                        "Formato de ID inválido",
                        status = HttpStatusCode.BadRequest
                    )
                }

                val usuario = service.getUsuario(id)
                if (usuario != null) {
                    call.respond(usuario)
                } else {
                    call.respondText("Usuario no encontrado", status = HttpStatusCode.NotFound)
                }
            } catch (e: Exception) {
                call.respondText(
                    "Error al obtener usuario: ${e.message}",
                    status = HttpStatusCode.InternalServerError
                )
            }
        }

        // Actualizar usuario
        put("/{id}") {
            try {
                val idString = call.parameters["id"] ?: return@put call.respondText(
                    "ID requerido",
                    status = HttpStatusCode.BadRequest
                )

                val id = try {
                    UUID.fromString(idString)
                } catch (e: IllegalArgumentException) {
                    return@put call.respondText(
                        "Formato de ID inválido",
                        status = HttpStatusCode.BadRequest
                    )
                }

                val params = call.receiveParameters()
                val nombre = params["nombre"] ?: return@put call.respondText(
                    "Nombre requerido",
                    status = HttpStatusCode.BadRequest
                )
                val user = params["user"] ?: return@put call.respondText(
                    "Usuario requerido",
                    status = HttpStatusCode.BadRequest
                )
                val cargoString = params["cargo"] ?: return@put call.respondText(
                    "Cargo requerido",
                    status = HttpStatusCode.BadRequest
                )

                val cargo = try {
                    UUID.fromString(cargoString)
                } catch (e: IllegalArgumentException) {
                    return@put call.respondText(
                        "Formato de cargo inválido",
                        status = HttpStatusCode.BadRequest
                    )
                }

                val usuario = service.updateUsuario(id, nombre, user, cargo)
                if (usuario != null) {
                    call.respond(usuario)
                } else {
                    call.respondText("Usuario no encontrado", status = HttpStatusCode.NotFound)
                }
            } catch (e: Exception) {
                call.respondText(
                    "Error al actualizar usuario: ${e.message}",
                    status = HttpStatusCode.InternalServerError
                )
            }
        }

        // Eliminar usuario
        delete("/{id}") {
            try {
                val idString = call.parameters["id"] ?: return@delete call.respondText(
                    "ID requerido",
                    status = HttpStatusCode.BadRequest
                )

                val id = try {
                    UUID.fromString(idString)
                } catch (e: IllegalArgumentException) {
                    return@delete call.respondText(
                        "Formato de ID inválido",
                        status = HttpStatusCode.BadRequest
                    )
                }

                if (service.deleteUsuario(id)) {
                    call.respondText("Usuario eliminado correctamente", status = HttpStatusCode.OK)
                } else {
                    call.respondText("Usuario no encontrado", status = HttpStatusCode.NotFound)
                }
            } catch (e: Exception) {
                call.respondText(
                    "Error al eliminar usuario: ${e.message}",
                    status = HttpStatusCode.InternalServerError
                )
            }
        }
    }

    // Autenticación
    post("/login") {
        try {
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
        } catch (e: Exception) {
            call.respondText(
                "Error en autenticación: ${e.message}",
                status = HttpStatusCode.InternalServerError
            )
        }
    }
}