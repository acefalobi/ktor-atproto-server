package social.aceinpink.routing

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import social.aceinpink.service.ATProtoServerService
import social.aceinpink.util.getAndValidate
import social.aceinpink.util.getNullable
import social.aceinpink.util.getAccountIdentifier
import social.aceinpink.util.ok

private val xrpcService = ATProtoServerService

fun Route.atProtoServerRoute() {
    post("/com.atproto.server.createAccount") {
        val params = call.receive<Map<String, Any>>()
        val email = params.getAndValidate<String>("email")
        val handle = params.getAndValidate<String>("handle")
        val password = params.getAndValidate<String>("password")
        val inviteCode = params.getNullable<String>("inviteCode")
        val recoveryKey = params.getNullable<String>("recoveryKey")
        call.ok(xrpcService.createAccount(email, handle, password, inviteCode, recoveryKey))
    }
    post("/com.atproto.server.createSession") {
        val params = call.receive<Map<String, Any>>()
        val identifier = params.getAndValidate<String>("identifier")
        val password = params.getAndValidate<String>("password")
        call.ok(xrpcService.createSession(identifier, password))
    }
    post("/com.atproto.server.deleteAccount") {
        val params = call.receive<Map<String, Any>>()
        val did = params.getAndValidate<String>("did")
        val password = params.getAndValidate<String>("password")
        val token = params.getAndValidate<String>("token")
        call.ok(xrpcService.deleteAccount(did, password, token))
    }
    get("/com.atproto.server.describeServer") {
        call.ok(xrpcService.describeServer())
    }
    post("/com.atproto.server.requestPasswordReset") {
        val params = call.receive<Map<String, Any>>()
        val email = params.getAndValidate<String>("email")
        call.ok(xrpcService.requestPasswordReset(email))
    }
    post("/com.atproto.server.resetPassword") {
        val params = call.receive<Map<String, Any>>()
        val token = params.getAndValidate<String>("token")
        val password = params.getAndValidate<String>("password")
        call.ok(xrpcService.resetPassword(token, password))
    }

    authenticate("xrpc-jwt") {
        post("/com.atproto.server.createAppPassword") {
            val params = call.receive<Map<String, Any>>()
            val appName = params.getAndValidate<String>("name")
            call.ok(xrpcService.createAppPassword(appName, call.getAccountIdentifier()))
        }
        post("/com.atproto.server.createInviteCode") {
            val params = call.receive<Map<String, Any>>()
            val useCount = params.getAndValidate<Double>("useCount").toInt()
            call.ok(xrpcService.createInviteCode(useCount, call.getAccountIdentifier()))
        }
        post("/com.atproto.server.createInviteCodes") {
            xrpcService.createInviteCodes(-1, -1, call.getAccountIdentifier())
        }
        post("/com.atproto.server.deleteSession") {
            call.ok(xrpcService.deleteSession(call.getAccountIdentifier()))
        }
        get("/com.atproto.server.getAccountInviteCodes") {
            val includeUsed = call.parameters["includeUsed"]?.toBoolean() ?: true
            val createAvailable = call.parameters["createAvailable"]?.toBoolean() ?: true
            call.ok(xrpcService.getAccountInviteCodes(includeUsed, createAvailable, call.getAccountIdentifier()))
        }
        get("/com.atproto.server.getSession") {
            call.ok(xrpcService.getSession(call.getAccountIdentifier()))
        }
        get("/com.atproto.server.listAppPasswords") {
            call.ok(xrpcService.listAppPasswords(call.getAccountIdentifier()))
        }
        post("/com.atproto.server.refreshSession") {
            call.ok(xrpcService.refreshSession(call.getAccountIdentifier()))
        }
        post("/com.atproto.server.requestAccountDelete") {
            call.ok(xrpcService.requestAccountDelete(call.getAccountIdentifier()))
        }
        post("/com.atproto.server.revokeAppPassword") {
            val params = call.receive<Map<String, Any>>()
            val appName = params.getAndValidate<String>("name")
            call.ok(xrpcService.revokeAppPassword(appName, call.getAccountIdentifier()))
        }
    }
}