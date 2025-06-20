package com.example.routes

import com.example.models.User
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route

private val users= mutableListOf(
    User ("1","admin", "admin"),
)

fun Route.userRouting() {
    route("/user") {
        get {
            if (users.isNotEmpty()) {
                call.respond(users)
            } else {
                call.respondText("No hay usuarios registrados", status = HttpStatusCode.NotFound)
            }
        }

        get ("{id}"){
            val id = call.parameters["id"]?: return@get call.respondText(
                "Usuario no encontrado",
                status = HttpStatusCode.BadRequest
            )
            val user = users.find { it.id == id }?: return@get call.respondText(
                "Usuario $id no encontrado",
                status = HttpStatusCode.NotFound
            )
            call.respond(user)
        }
        post {
            val user = call.receive<User>()
            users.add(user)
            call.respondText("Usuario guardado con exito", status = HttpStatusCode.Created)
        }

        delete ("{id}") {
            val id = call.parameters["id"] ?: return@delete call.respondText(
                "Usuario no encontrado",
                status = HttpStatusCode.BadRequest
            )
            if (users.removeIf { it.id == id }) {
                call.respondText("Usuario $id eliminado con exito", status = HttpStatusCode.OK)
            } else {
                call.respondText("Usuario $id no encontrado", status = HttpStatusCode.NotFound)
            }
        }
    }
}