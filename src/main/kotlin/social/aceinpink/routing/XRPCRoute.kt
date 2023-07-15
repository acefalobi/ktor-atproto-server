package social.aceinpink.routing

import io.ktor.server.routing.*
import social.aceinpink.exception.ResponseError
import social.aceinpink.exception.ResponseException
import social.aceinpink.service.ATProtoServerService

private val xrpcService = ATProtoServerService

fun Routing.xrpcRoute() {
    route("/xrpc") {
        atProtoServerRoute()

        get("/*") {
            throw ResponseException(
                ResponseError.MethodNotImplemented,
                "This method has not yet been implemented on this server."
            )
        }
    }
}