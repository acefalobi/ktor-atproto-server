package social.aceinpink.exception

import social.aceinpink.service.XRPCService

enum class ResponseError(val defaultMessage: String = "") {
    // -------------------------------------------------------------
    // XRPC Errors.
    // -------------------------------------------------------------

    /**
     * The handle given is invalid.
     *
     * Used in [XRPCService.createAccount]
     */
    InvalidHandle,

    /**
     * The password given is invalid.
     *
     * Used in [XRPCService.createAccount]
     */
    InvalidPassword,

    /**
     * The invite code given is invalid.
     *
     * Used in [XRPCService.createAccount]
     */
    InvalidInviteCode,

    /**
     * The handle isn't available for registration.
     *
     * Used in [XRPCService.createAccount].
     */
    HandleNotAvailable,

    /**
     * The request domain is not supported.
     *
     * Used in [XRPCService.createAccount]
     */
    UnsupportedDomain,

    /**
     * The account being requested was taken down.
     *
     * Used in
     * [XRPCService.createInviteCode],
     * [XRPCService.createSession],
     * [XRPCService.listAppPasswords],
     * [XRPCService.refreshSession].
     */
    AccountTakedown,

    /**
     * The requested XRPC method is not implemented on this server.
     *
     * Used in unsupported methods.
     */
    MethodNotImplemented("This method is not supported on this server."),

    /**
     * The given token has passed its expiry date.
     *
     * Used in [XRPCService.deleteAccount], [XRPCService.resetPassword].
     */
    ExpiredToken,

    /**
     * The given token is not valid.
     *
     * Used in [XRPCService.deleteAccount], [XRPCService.resetPassword].
     */
    InvalidToken,

    /**
     * The item was already created(?).
     *
     * Used in [XRPCService.getAccountInviteCodes].
     */
    DuplicateCreate,


    // -------------------------------------------------------------
    // Request Errors.
    // -------------------------------------------------------------

    /**
     * An invalid request body was received.
     */
    InvalidRequest("Invalid request body was received."),

    /**
     * An invalid request body was received.
     */
    AuthenticationRequired("Authentication is required for this operation."),


    // -------------------------------------------------------------
    // Other Errors.
    // -------------------------------------------------------------

    /**
     * An unknown error was thrown.
     */
    UnknownError,
}