package com.example.plugins

import com.example.repositories.*
import com.example.routes.*
import com.example.services.*
import io.ktor.server.application.*
import io.ktor.server.response.respondText
import io.ktor.server.routing.*

fun Application.configureRouting() {
    // Inicializar repositorios
    val cargoRepository = CargoRepository()
    val usuarioRepository = UsuarioRepository()
    val almacenRepository = AlmacenRepository()
    val prendaRepository = PrendaRepository() // Nuevo repositorio
    val packingListRepository = PackingListRepository()
    val detallePackingListRepository = DetallePackingListRepository()
    val detalleInventarioAlmacenRepository = DetalleInventarioAlmacenRepository()

    // Inicializar servicios
    val cargoService = CargoService(cargoRepository)
    val usuarioService = UsuarioService(usuarioRepository)
    val almacenService = AlmacenService(almacenRepository)
    val prendaService = PrendaService(prendaRepository) // Nuevo servicio
    val packingListService = PackingListService(packingListRepository)
    val detallePackingListService = DetallePackingListService(detallePackingListRepository)
    val detalleInventarioAlmacenService = DetalleInventarioAlmacenService(detalleInventarioAlmacenRepository)

    routing {
        cargoRoutes(cargoService)
        userRoutes(usuarioService)
        almacenRoutes(almacenService)
        prendaRoutes(prendaService) // Nuevas rutas
        packingListRoutes(packingListService)
        detallePackingListRoutes(detallePackingListService)
        detalleInventarioAlmacenRoutes(detalleInventarioAlmacenService)

        get("/") {
            call.respondText("API de Inventario Textiles")
        }
    }
}