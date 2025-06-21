package com.example.routes

import com.example.services.AlmacenService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.almacenRoutes(service: AlmacenService) {
    route("/almacenes") {
        // Crear almacén
        post {
            val params = call.receiveParameters()
            val nombre = params["nombre"] ?: return@post call.respondText(
                "Nombre requerido",
                status = HttpStatusCode.BadRequest
            )
            val personal = UUID.fromString(params["personal"] ?: return@post call.respondText(
                "Personal requerido",
                status = HttpStatusCode.BadRequest
            ))

            val almacen = service.createAlmacen(nombre, personal)
            call.respond(almacen)
        }

        // Obtener almacén por ID
        get("/{id}") {
            val id = UUID.fromString(call.parameters["id"] ?: return@get call.respondText(
                "ID requerido",
                status = HttpStatusCode.BadRequest
            ))

            val almacen = service.getAlmacen(id)
            if (almacen != null) {
                call.respond(almacen)
            } else {
                call.respondText("Almacén no encontrado", status = HttpStatusCode.NotFound)
            }
        }

        // Actualizar almacén
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
            val personal = UUID.fromString(params["personal"] ?: return@put call.respondText(
                "Personal requerido",
                status = HttpStatusCode.BadRequest
            ))

            val almacen = service.updateAlmacen(id, nombre, personal)
            if (almacen != null) {
                call.respond(almacen)
            } else {
                call.respondText("Almacén no encontrado", status = HttpStatusCode.NotFound)
            }
        }

        // Eliminar almacén
        delete("/{id}") {
            val id = UUID.fromString(call.parameters["id"] ?: return@delete call.respondText(
                "ID requerido",
                status = HttpStatusCode.BadRequest
            ))

            if (service.deleteAlmacen(id)) {
                call.respondText("Almacén eliminado correctamente")
            } else {
                call.respondText("Almacén no encontrado", status = HttpStatusCode.NotFound)
            }
        }

        // Listar todos los almacenes
        get {
            val almacenes = service.listAlmacenes()
            call.respond(almacenes)
        }
    }
}