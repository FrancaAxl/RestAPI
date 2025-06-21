package com.example.routes

import com.example.services.PackingListService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.packingListRoutes(service: PackingListService) {
    route("/packing-lists") {
        // Crear packing list
        post {
            val params = call.receiveParameters()
            val folio = params["folio"] ?: return@post call.respondText(
                "Folio requerido",
                status = HttpStatusCode.BadRequest
            )
            val envia = params["envia"] ?: return@post call.respondText(
                "Envía requerido",
                status = HttpStatusCode.BadRequest
            )
            val recibe = params["recibe"] ?: return@post call.respondText(
                "Recibe requerido",
                status = HttpStatusCode.BadRequest
            )
            val transporte = params["transporte"] ?: return@post call.respondText(
                "Transporte requerido",
                status = HttpStatusCode.BadRequest
            )
            val codigoContenedor = params["codigoContenedor"] ?: return@post call.respondText(
                "Código de contenedor requerido",
                status = HttpStatusCode.BadRequest
            )

            val packingList = service.createPackingList(
                folio, envia, recibe, transporte, codigoContenedor
            )
            call.respond(packingList)
        }

        // Obtener packing list por ID
        get("/{id}") {
            val id = UUID.fromString(call.parameters["id"] ?: return@get call.respondText(
                "ID requerido",
                status = HttpStatusCode.BadRequest
            ))

            val packingList = service.getPackingList(id)
            if (packingList != null) {
                call.respond(packingList)
            } else {
                call.respondText("Packing list no encontrada", status = HttpStatusCode.NotFound)
            }
        }

        // Actualizar packing list
        put("/{id}") {
            val id = UUID.fromString(call.parameters["id"] ?: return@put call.respondText(
                "ID requerido",
                status = HttpStatusCode.BadRequest
            ))

            val params = call.receiveParameters()
            val folio = params["folio"] ?: return@put call.respondText(
                "Folio requerido",
                status = HttpStatusCode.BadRequest
            )
            val envia = params["envia"] ?: return@put call.respondText(
                "Envía requerido",
                status = HttpStatusCode.BadRequest
            )
            val recibe = params["recibe"] ?: return@put call.respondText(
                "Recibe requerido",
                status = HttpStatusCode.BadRequest
            )
            val transporte = params["transporte"] ?: return@put call.respondText(
                "Transporte requerido",
                status = HttpStatusCode.BadRequest
            )
            val codigoContenedor = params["codigoContenedor"] ?: return@put call.respondText(
                "Código de contenedor requerido",
                status = HttpStatusCode.BadRequest
            )

            val packingList = service.updatePackingList(
                id, folio, envia, recibe, transporte, codigoContenedor
            )
            if (packingList != null) {
                call.respond(packingList)
            } else {
                call.respondText("Packing list no encontrada", status = HttpStatusCode.NotFound)
            }
        }

        // Eliminar packing list
        delete("/{id}") {
            val id = UUID.fromString(call.parameters["id"] ?: return@delete call.respondText(
                "ID requerido",
                status = HttpStatusCode.BadRequest
            ))

            if (service.deletePackingList(id)) {
                call.respondText("Packing list eliminada correctamente")
            } else {
                call.respondText("Packing list no encontrada", status = HttpStatusCode.NotFound)
            }
        }

        // Listar todas las packing lists
        get {
            val packingLists = service.listPackingLists()
            call.respond(packingLists)
        }
    }
}