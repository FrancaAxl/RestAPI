package com.example

import com.example.plugins.DatabaseConfig
import com.example.plugins.DatabaseFactory
import com.example.plugins.configureRouting
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module).start(wait = true)
}

fun Application.module() {
    // Configuración de la base de datos
    DatabaseFactory.init(
        DatabaseConfig(
            url = "jdbc:mysql://localhost:3306/InventarioTextiles",
            user = "root",
            password = "1234",
            poolSize = 10,
            showSql = true // Solo en desarrollo
        )
    )

    // Configurar rutas
    configureRouting()
}