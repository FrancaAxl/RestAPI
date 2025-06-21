import com.example.plugins.DatabaseFactory
import com.example.repositories.UsuarioRepository
import com.example.routes.userRoutes
import com.example.services.UsuarioService
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.routing.routing

fun main() {
    embeddedServer(Netty, port = 8080, module = Application::module).start(wait = true)
}

fun Application.module() {
    // Configuración de la base de datos
    DatabaseFactory.init()

    // Inicialización de servicios
    val usuarioService = UsuarioService(UsuarioRepository())

    // Configuración de rutas
    routing {
        userRoutes(usuarioService)
    }
}