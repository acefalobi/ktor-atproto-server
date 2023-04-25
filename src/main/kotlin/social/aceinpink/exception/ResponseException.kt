package social.aceinpink.exception

class ResponseException(val error: ResponseError, override val message: String = error.defaultMessage) : Error(message)
