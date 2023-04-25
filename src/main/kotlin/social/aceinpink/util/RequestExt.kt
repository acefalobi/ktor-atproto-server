package social.aceinpink.util

import io.ktor.server.application.*
import io.ktor.server.auth.*
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

fun ApplicationCall.getUserId(): String {
    val id = authentication.principal<UserIdPrincipal>()?.name
    return id ?: throw ResponseException(ResponseError.AuthenticationRequired)
}