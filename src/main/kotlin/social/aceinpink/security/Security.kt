package social.aceinpink.security

import io.ktor.server.application.*
import io.ktor.server.auth.*

fun Application.configureSecurity() {

    install(Authentication) {
        bearer("xrpc") {
            authenticate { tokenCredential ->
                if (tokenCredential.token == "testToken") { // TODO: Replace with account service check.
                    UserIdPrincipal("testUser") // TODO: Replace with user's identifier.
                } else {
                    null
                }
            }
        }
    }
}