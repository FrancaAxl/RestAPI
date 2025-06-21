package com.example.routes

fun Route.syncRoutes() {
    val syncService by inject<SyncService>()

    route("/api/sync") {
        authenticate {
            post("/push") {
                val deviceId = call.request.headers["Device-ID"]
                    ?: throw IllegalArgumentException("Device-ID header is required")

                val syncRequest = call.receive<SyncRequest>()
                val response = syncService.processIncomingChanges(syncRequest, deviceId)

                call.respond(HttpStatusCode.OK, response)
            }

            get("/pull") {
                val deviceId = call.request.headers["Device-ID"]
                    ?: throw IllegalArgumentException("Device-ID header is required")

                val lastSync = call.request.queryParameters["lastSync"]?.toLongOrNull()
                val response = syncService.getPendingChanges(lastSync, deviceId)

                call.respond(HttpStatusCode.OK, response)
            }

            webSocket("/updates") {
                val deviceId = call.request.headers["Device-ID"]
                    ?: throw IllegalArgumentException("Device-ID header is required")

                syncService.registerDevice(this, deviceId)

                try {
                    for (frame in incoming) {
                        frame as? Frame.Text ?: continue
                        val message = frame.readText()
                        // Manejar mensajes WebSocket
                    }
                } catch (e: Exception) {
                    syncService.unregisterDevice(deviceId)
                }
            }
        }
    }
}