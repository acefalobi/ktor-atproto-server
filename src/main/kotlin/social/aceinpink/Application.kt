package social.aceinpink

import io.ktor.http.*
import io.ktor.serialization.gson.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.defaultheaders.*
import io.ktor.server.plugins.statuspages.*
import social.aceinpink.exception.ResponseError
import social.aceinpink.exception.ResponseException
import social.aceinpink.routing.configureRouting
import social.aceinpink.security.configureSecurity
import social.aceinpink.util.error
import java.text.DateFormat

fun main() {
    val environment = applicationEngineEnvironment {
        developmentMode = true
        connector {
            port = 8080
            host = "0.0.0.0"
        }
        watchPaths = listOf("classes")
        module {
            module()
        }
    }
    embeddedServer(Netty, environment).start(wait = true)
}

fun Application.module() {
    install(StatusPages) {
        exception<ResponseException> { call, cause ->
            call.error(cause)
        }
        exception<BadRequestException> { call, _ ->
            call.error(ResponseException(ResponseError.InvalidRequest))
        }
        exception<Throwable> { call, cause ->
            call.error(ResponseException(ResponseError.UnknownError, cause.localizedMessage ?: ""))
        }
        status(HttpStatusCode.Unauthorized) { call, _ ->
            call.error(ResponseException(ResponseError.AuthenticationRequired))
        }
    }
    install(ContentNegotiation) {
        gson {
            setDateFormat(DateFormat.FULL)
            setPrettyPrinting()
        }
    }
    install(CORS) {
        anyHost()
        allowHeader(HttpHeaders.ContentType)
        allowHeaders { true }
    }
    install(DefaultHeaders)
    configureSecurity()
    configureRouting()
}
