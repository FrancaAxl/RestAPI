package com.example.routes

import com.example.services.PrendaService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.prendaRoutes(service: PrendaService) {
    route("/prendas") {
        // Crear prenda
        post {
            val params = call.receiveParameters()
            val codigoBarras = params["codigoBarras"] ?: return@post call.respondText(
                "Código de barras requerido",
                status = HttpStatusCode.BadRequest
            )
            val nombreProducto = params["nombreProducto"] ?: return@post call.respondText(
                "Nombre del producto requerido",
                status = HttpStatusCode.BadRequest
            )
            val talla = params["talla"] ?: return@post call.respondText(
                "Talla requerida",
                status = HttpStatusCode.BadRequest
            )
            val color = params["color"] ?: return@post call.respondText(
                "Color requerido",
                status = HttpStatusCode.BadRequest
            )

            val prenda = service.createPrenda(codigoBarras, nombreProducto, talla, color)
            call.respond(prenda)
        }

        // Obtener prenda por ID
        get("/{id}") {
            val id = UUID.fromString(call.parameters["id"] ?: return@get call.respondText(
                "ID requerido",
                status = HttpStatusCode.BadRequest
            ))

            val prenda = service.getPrenda(id)
            if (prenda != null) {
                call.respond(prenda)
            } else {
                call.respondText("Prenda no encontrada", status = HttpStatusCode.NotFound)
            }
        }

        // Obtener prenda por código de barras
        get("/codigo/{codigo}") {
            val codigo = call.parameters["codigo"] ?: return@get call.respondText(
                "Código requerido",
                status = HttpStatusCode.BadRequest
            )

            val prenda = service.getPrendaByCodigo(codigo)
            if (prenda != null) {
                call.respond(prenda)
            } else {
                call.respondText("Prenda no encontrada", status = HttpStatusCode.NotFound)
            }
        }

        // Actualizar prenda
        put("/{id}") {
            val id = UUID.fromString(call.parameters["id"] ?: return@put call.respondText(
                "ID requerido",
                status = HttpStatusCode.BadRequest
            ))

            val params = call.receiveParameters()
            val codigoBarras = params["codigoBarras"] ?: return@put call.respondText(
                "Código de barras requerido",
                status = HttpStatusCode.BadRequest
            )
            val nombreProducto = params["nombreProducto"] ?: return@put call.respondText(
                "Nombre del producto requerido",
                status = HttpStatusCode.BadRequest
            )
            val talla = params["talla"] ?: return@put call.respondText(
                "Talla requerida",
                status = HttpStatusCode.BadRequest
            )
            val color = params["color"] ?: return@put call.respondText(
                "Color requerido",
                status = HttpStatusCode.BadRequest
            )

            val prenda = service.updatePrenda(id, codigoBarras, nombreProducto, talla, color)
            if (prenda != null) {
                call.respond(prenda)
            } else {
                call.respondText("Prenda no encontrada", status = HttpStatusCode.NotFound)
            }
        }

        // Eliminar prenda
        delete("/{id}") {
            val id = UUID.fromString(call.parameters["id"] ?: return@delete call.respondText(
                "ID requerido",
                status = HttpStatusCode.BadRequest
            ))

            if (service.deletePrenda(id)) {
                call.respondText("Prenda eliminada correctamente")
            } else {
                call.respondText("Prenda no encontrada", status = HttpStatusCode.NotFound)
            }
        }

        // Listar todas las prendas
        get {
            val prendas = service.listPrendas()
            call.respond(prendas)
        }

        // Obtener prendas no sincronizadas
        get("/no-sincronizadas") {
            val prendas = service.getPrendasNoSincronizadas()
            call.respond(prendas)
        }
    }
}