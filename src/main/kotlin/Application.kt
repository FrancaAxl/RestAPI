package com.example

import io.ktor.server.application.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main(args: Array<String>) {
    embeddedServer(Netty, port = 8080,host= "127.0.0.1", module = Application::module).start(wait = true)




    }


fun Application.module() {
    configureSerialization()
    configureRouting()
}
