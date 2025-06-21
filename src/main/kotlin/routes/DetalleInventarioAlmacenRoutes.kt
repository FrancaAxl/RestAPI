package com.example.routes

import com.example.services.DetalleInventarioAlmacenService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.detalleInventarioAlmacenRoutes(service: DetalleInventarioAlmacenService) {
    route("/inventario-almacen") {
        // Crear detalle
        post {
            val params = call.receiveParameters()
            val almacen = UUID.fromString(params["almacen"] ?: return@post call.respondText(
                "Almacen requerido",
                status = HttpStatusCode.BadRequest
            ))
            val prendas = UUID.fromString(params["prendas"] ?: return@post call.respondText(
                "Prendas requerido",
                status = HttpStatusCode.BadRequest
            ))
            val cantidad = params["cantidad"]?.toIntOrNull() ?: return@post call.respondText(
                "Cantidad inválida",
                status = HttpStatusCode.BadRequest
            )

            val detalle = service.createDetalle(almacen, prendas, cantidad)
            call.respond(detalle)
        }

        // Obtener detalle por ID
        get("/{id}") {
            val id = UUID.fromString(call.parameters["id"] ?: return@get call.respondText(
                "ID requerido",
                status = HttpStatusCode.BadRequest
            ))

            val detalle = service.getDetalle(id)
            if (detalle != null) {
                call.respond(detalle)
            } else {
                call.respondText("Detalle no encontrado", status = HttpStatusCode.NotFound)
            }
        }

        // Actualizar detalle
        put("/{id}") {
            val id = UUID.fromString(call.parameters["id"] ?: return@put call.respondText(
                "ID requerido",
                status = HttpStatusCode.BadRequest
            ))

            val params = call.receiveParameters()
            val almacen = UUID.fromString(params["almacen"] ?: return@put call.respondText(
                "Almacen requerido",
                status = HttpStatusCode.BadRequest
            ))
            val prendas = UUID.fromString(params["prendas"] ?: return@put call.respondText(
                "Prendas requerido",
                status = HttpStatusCode.BadRequest
            ))
            val cantidad = params["cantidad"]?.toIntOrNull() ?: return@put call.respondText(
                "Cantidad inválida",
                status = HttpStatusCode.BadRequest
            )

            val detalle = service.updateDetalle(id, almacen, prendas, cantidad)
            if (detalle != null) {
                call.respond(detalle)
            } else {
                call.respondText("Detalle no encontrado", status = HttpStatusCode.NotFound)
            }
        }

        // Eliminar detalle
        delete("/{id}") {
            val id = UUID.fromString(call.parameters["id"] ?: return@delete call.respondText(
                "ID requerido",
                status = HttpStatusCode.BadRequest
            ))

            if (service.deleteDetalle(id)) {
                call.respondText("Detalle eliminado correctamente")
            } else {
                call.respondText("Detalle no encontrado", status = HttpStatusCode.NotFound)
            }
        }

        // Listar todos los detalles
        get {
            val detalles = service.listDetalles()
            call.respond(detalles)
        }
    }
}