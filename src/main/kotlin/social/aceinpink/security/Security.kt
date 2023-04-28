package social.aceinpink.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import social.aceinpink.exception.ResponseError
import social.aceinpink.exception.ResponseException

var JWT_SECRET = ""
    private set

fun Application.configureSecurity() {
    JWT_SECRET = environment.config.property("jwt.secret").getString()
    install(Authentication) {
        jwt("xrpc-jwt") {
            verifier(JWT.require(Algorithm.HMAC512(JWT_SECRET.toByteArray())).build())

            validate { credential ->
                credential.payload.subject
                if (!credential.payload.subject.isNullOrEmpty()) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }

            challenge { _, _ ->
                throw ResponseException(ResponseError.AuthenticationRequired)
            }
        }
    }
}