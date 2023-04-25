package social.aceinpink.util

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import social.aceinpink.exception.ResponseError
import social.aceinpink.exception.ResponseException


suspend inline fun <reified T : Any> ApplicationCall.ok(response: T) {
    respond(HttpStatusCode.OK, response)
}

suspend fun ApplicationCall.error(exception: ResponseException) {
    val httpStatusCode = when (exception.error) {
        ResponseError.InvalidRequest,
        ResponseError.InvalidHandle,
        ResponseError.InvalidPassword,
        ResponseError.InvalidInviteCode,
        ResponseError.HandleNotAvailable,
        ResponseError.UnsupportedDomain,
        ResponseError.DuplicateCreate,
        ResponseError.AccountTakedown -> HttpStatusCode.BadRequest

        ResponseError.AuthenticationRequired,
        ResponseError.ExpiredToken,
        ResponseError.InvalidToken -> HttpStatusCode.Unauthorized

        ResponseError.MethodNotImplemented -> HttpStatusCode.NotImplemented
        ResponseError.UnknownError -> HttpStatusCode.InternalServerError
    }
    respond(httpStatusCode, mapOf("error" to exception.error, "message" to exception.message))
}