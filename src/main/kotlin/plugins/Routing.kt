package com.example.plugins

import com.example.repositories.UsuarioRepository
import com.example.routes.userRoutes
import com.example.services.UsuarioService
import io.ktor.server.application.*
import io.ktor.server.response.respondText
import io.ktor.server.routing.*

fun Application.configureRouting() {
    // Inicializar servicios
    val usuarioService = UsuarioService(UsuarioRepository())

    routing {
        // Configurar rutas
        userRoutes(usuarioService)

        // Otras rutas pueden ir aquí
        get("/") {
            call.respondText("API de Inventario Textiles")
        }
    }
}