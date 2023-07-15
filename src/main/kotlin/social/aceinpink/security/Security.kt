package social.aceinpink.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import social.aceinpink.ServerConfig
import social.aceinpink.exception.ResponseError
import social.aceinpink.exception.ResponseException
import java.security.MessageDigest




fun Application.configureSecurity() {
    install(Authentication) {
        jwt("xrpc-jwt") {
            verifier(JWT.require(Algorithm.HMAC512(ServerConfig.JWT_SECRET.toByteArray())).build())

            validate { credential ->
                credential.payload.subject
                if (!credential.payload.subject.isNullOrEmpty()) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }

            challenge { _, _ ->
                throw ResponseException(ResponseError.InvalidToken)
            }
        }
    }
}

fun sha256(message: ByteArray): ByteArray {
    val digest = MessageDigest.getInstance("SHA-256")
    digest.update(message)
    return digest.digest()
}