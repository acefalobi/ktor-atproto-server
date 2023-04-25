package social.aceinpink.model

data class ServerDescription(
    val availableUserDomains: List<String>,
    val inviteCodeRequired: Boolean,
    val links: Links,
) {
    data class Links(
        val privacyPolicy: String,
        val termsOfService: String,
    )
}
