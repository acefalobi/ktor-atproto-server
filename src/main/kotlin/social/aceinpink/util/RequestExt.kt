package social.aceinpink.util

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import social.aceinpink.exception.ResponseError
import social.aceinpink.exception.ResponseException

inline fun <reified T> Map<String, Any?>.getAndValidate(key: String): T {
    return (get(key) as? T) ?: throw ResponseException(
        ResponseError.InvalidRequest,
        "$key field is required"
    )
}

inline fun <reified T> Map<String, Any?>.getNullable(key: String): T? {
    return get(key) as? T
}

fun ApplicationCall.getAccountIdentifier(): String {
    val identifier = authentication.principal<JWTPrincipal>()?.subject
    return identifier ?: throw ResponseException(ResponseError.AuthenticationRequired)
}