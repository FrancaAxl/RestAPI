package com.example.routes

import com.example.services.CargoService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.cargoRoutes(service: CargoService) {
    route("/cargos") {
        // Crear cargo
        post {
            val params = call.receiveParameters()
            val nombreCargo = params["nombreCargo"] ?: return@post call.respondText(
                "Nombre de cargo requerido",
                status = HttpStatusCode.BadRequest
            )
            val nivelCargo = params["nivelCargo"]?.toIntOrNull() ?: return@post call.respondText(
                "Nivel de cargo inválido",
                status = HttpStatusCode.BadRequest
            )

            val cargo = service.createCargo(nombreCargo, nivelCargo)
            call.respond(cargo)
        }

        // Obtener cargo por ID
        get("/{id}") {
            val id = UUID.fromString(call.parameters["id"] ?: return@get call.respondText(
                "ID requerido",
                status = HttpStatusCode.BadRequest
            ))

            val cargo = service.getCargo(id)
            if (cargo != null) {
                call.respond(cargo)
            } else {
                call.respondText("Cargo no encontrado", status = HttpStatusCode.NotFound)
            }
        }

        // Actualizar cargo
        put("/{id}") {
            val id = UUID.fromString(call.parameters["id"] ?: return@put call.respondText(
                "ID requerido",
                status = HttpStatusCode.BadRequest
            ))

            val params = call.receiveParameters()
            val nombreCargo = params["nombreCargo"] ?: return@put call.respondText(
                "Nombre de cargo requerido",
                status = HttpStatusCode.BadRequest
            )
            val nivelCargo = params["nivelCargo"]?.toIntOrNull() ?: return@put call.respondText(
                "Nivel de cargo inválido",
                status = HttpStatusCode.BadRequest
            )

            val cargo = service.updateCargo(id, nombreCargo, nivelCargo)
            if (cargo != null) {
                call.respond(cargo)
            } else {
                call.respondText("Cargo no encontrado", status = HttpStatusCode.NotFound)
            }
        }

        // Eliminar cargo
        delete("/{id}") {
            val id = UUID.fromString(call.parameters["id"] ?: return@delete call.respondText(
                "ID requerido",
                status = HttpStatusCode.BadRequest
            ))

            if (service.deleteCargo(id)) {
                call.respondText("Cargo eliminado correctamente")
            } else {
                call.respondText("Cargo no encontrado", status = HttpStatusCode.NotFound)
            }
        }

        // Listar todos los cargos
        get {
            val cargos = service.listCargos()
            call.respond(cargos)
        }
    }
}