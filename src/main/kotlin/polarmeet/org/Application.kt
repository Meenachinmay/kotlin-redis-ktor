package polarmeet.org

import io.ktor.server.application.*
import io.ktor.serialization.jackson.*
import io.ktor.server.plugins.contentnegotiation.*
import polarmeet.org.plugins.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    install(ContentNegotiation) {
        jackson()
    }
    configureRouting()
}