package com.example.services

fun main() {
    embeddedServer(Netty, port = 8080, host = "127.0.0.1") {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }

        install(WebSockets)

        install(Authentication) {
            jwt {
                // Configuración de autenticación JWT
            }
        }

        DatabaseFactory.init()

        routing {
            authenticate {
                syncRoutes()
                // otras rutas...
            }
        }
    }.start(wait = true)
}
// En tu Application.kt
fun Application.module() {
    DatabaseFactory.init(DatabaseConfig(
        url = environment.config.property("ktor.database.url").getString(),
        user = environment.config.property("ktor.database.user").getString(),
        password = environment.config.property("ktor.database.password").getString(),
        showSql = true // Solo en desarrollo
    ))

    // Resto de tu configuración...
}