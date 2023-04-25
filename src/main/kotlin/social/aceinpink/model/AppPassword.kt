package social.aceinpink.model

import java.util.Date


data class AppPassword(
    val name: String,
    val password: String? = null,
    val createdAt: Date = Date(),
)