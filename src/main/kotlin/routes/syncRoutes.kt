package com.example.routes

import com.example.services.SyncService
import com.example.services.SyncedItem
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.syncRoutes(service: SyncService) {
    route("/sync") {
        get {
            val pendingData = service.getPendingSyncData()
            call.respond(pendingData)
        }

        post("/confirm") {
            val confirmedItems = call.receive<List<SyncedItem>>()
            service.confirmSync(confirmedItems)
            call.respond(mapOf("status" to "success"))
        }
    }
}