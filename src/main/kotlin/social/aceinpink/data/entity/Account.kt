package social.aceinpink.data.entity

data class Account(
    val email: String,
    val handle: String,
    val did: String,
    val passwordHash: String,
    val displayName: String? = null,
) : Entity()
