package social.aceinpink.routing

import io.ktor.server.application.*
import io.ktor.server.routing.*
import social.aceinpink.routing.xrpcRoute

fun Application.configureRouting() {
    routing {
        xrpcRoute()
    }
}