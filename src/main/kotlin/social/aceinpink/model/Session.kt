package social.aceinpink.model


data class Session(
    val accessJwt: String,
    val refreshJwt: String,
    val handle: String,
    val did: String,
    val email: String? = null,
)