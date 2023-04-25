package social.aceinpink.service

import social.aceinpink.exception.ResponseError
import social.aceinpink.exception.ResponseException
import social.aceinpink.model.AppPassword
import social.aceinpink.model.InviteCode
import social.aceinpink.model.InviteCodes
import social.aceinpink.model.ServerDescription
import social.aceinpink.model.Session
import social.aceinpink.model.SessionInfo
import java.util.Date

object XRPCService {

    /**
     * Create an account.
     *
     * See https://atproto.com/lexicons/com-atproto-server#comatprotoservercreateaccount
     */
    fun createAccount(
        email: String,
        handle: String,
        password: String,
        inviteCode: String? = null,
        recoveryKey: String? = null,
    ): Session {
        // TODO: Create new user entry according to ATProto specs and create session for the user.
        return Session("", "", "", "", "")
    }

    /**
     * Create an app-specific password.
     *
     * See https://atproto.com/lexicons/com-atproto-server#comatprotoservercreateapppassword
     */
    fun createAppPassword(name: String, accountId: String): AppPassword {
        // TODO: Create an app for the signed in user and generate a password for it.
        return AppPassword("", "", Date())
    }

    /**
     * Create an invite code.
     *
     * See https://atproto.com/lexicons/com-atproto-server#comatprotoservercreateinvitecode
     */
    fun createInviteCode(useCount: Int, account: String): InviteCode {
        // TODO: Create an invite code that can be used [useCount] number of times.
        return InviteCode("")
    }

    /**
     * Create invite codes.
     *
     * See https://atproto.com/lexicons/com-atproto-server#comatprotoservercreateinvitecodes
     */
    fun createInviteCodes(codeCount: Int, useCount: Int, account: String) {
        throw ResponseException(ResponseError.MethodNotImplemented, "Invite codes are not supported by this server.")
    }

    /**
     * Create an authentication session.
     *
     * See https://atproto.com/lexicons/com-atproto-server#comatprotoservercreatesession
     */
    fun createSession(identifier: String, password: String): Session {
        // TODO: Validate the identifier and create a session for the user if valid.
        return Session("", "", "", "", "")
    }

    /**
     * Delete a user account with a token and password.
     *
     * @param did the id of  the user.
     * @param password the password of the user.
     * @param token the account deletion token sent to the user's email.
     *
     * See https://atproto.com/lexicons/com-atproto-server#comatprotoserverdeleteaccount
     */
    fun deleteAccount(did: String, password: String, token: String) {
        // TODO: Validate the did, password, and token for the user before deleting the account.
    }

    /**
     * Delete the current session.
     *
     * See https://atproto.com/lexicons/com-atproto-server#comatprotoserverdeletesession
     */
    fun deleteSession(accountId: String) {
        // TODO: Delete the session of the authenticated user.
    }

    /**
     * Get a document describing the service's accounts configuration.
     *
     * See https://atproto.com/lexicons/com-atproto-server#comatprotoserverdescribeserver
     */
    fun describeServer(): ServerDescription {
        val privacyPolicy = "https://staging.bsky.app/support/privacy" // TODO: Change to own.
        val termsOfService = "https://staging.bsky.app/support/tos" // TODO: Change to own.
        return ServerDescription(
            listOf("aceinpink.social"),
            false,
            ServerDescription.Links(privacyPolicy, termsOfService)
        )
    }

    /**
     * Get all invite codes for a given account.
     *
     * @param includeUsed include used codes in the result.
     * @param createAvailable if there are available codes to be created for the account, then create them before
     *                        returning the result.
     *
     * See https://atproto.com/lexicons/com-atproto-server#comatprotoservergetaccountinvitecodes
     */
    fun getAccountInviteCodes(
        includeUsed: Boolean = true,
        createAvailable: Boolean = true,
        account: String
    ): InviteCodes {
        // TODO: Return all invite codes for this account.
        return InviteCodes(emptyList())
    }

    /**
     * Get information about the current session.
     *
     * See https://atproto.com/lexicons/com-atproto-server#comatprotoservergetsession
     */
    fun getSession(account: String): SessionInfo {
        // TODO: Get info about the current session.
        return SessionInfo("", "", "")
    }

    /**
     * List all app-specific passwords.
     *
     * See https://atproto.com/lexicons/com-atproto-server#comatprotoserverlistapppasswords
     */
    fun listAppPasswords(account: String): List<AppPassword> {
        // TODO: Get all created apps.
        return emptyList()
    }

    /**
     * Refresh an authentication session.
     *
     * See https://atproto.com/lexicons/com-atproto-server#comatprotoserverrefreshsession
     */
    fun refreshSession(account: String): Session {
        // TODO: Create a new jwt for the authenticated session.
        return Session("", "", "", "", "")
    }

    /**
     * Initiate a user account deletion via email.
     *
     * See https://atproto.com/lexicons/com-atproto-server#comatprotoserverrequestaccountdelete
     */
    fun requestAccountDelete(account: String) {
        // TODO: Create a token for account deletion and send it to the authenticated user's email.
    }

    /**
     * Initiate a user account password reset via email.
     *
     * See https://atproto.com/lexicons/com-atproto-server#comatprotoserverrequestpasswordreset
     */
    fun requestPasswordReset(email: String) {
        // TODO: Create a token for password reset and send it to [email].
    }

    /**
     * Reset a user account password using a token.
     *
     * @param password the password of the user.
     * @param token the account deletion token sent to the user's email.
     *
     * See https://atproto.com/lexicons/com-atproto-server#comatprotoserverresetpassword
     */
    fun resetPassword(password: String, token: String) {
        // TODO: Validate the [token] for the user before setting a new [password] for the account.
    }

    /**
     * Revoke an app-specific password by name.
     *
     * See https://atproto.com/lexicons/com-atproto-server#comatprotoserverrevokeapppassword
     */
    fun revokeAppPassword(name: String, account: String) {
        // TODO: Delete the entry for the app password with[name].
    }

}