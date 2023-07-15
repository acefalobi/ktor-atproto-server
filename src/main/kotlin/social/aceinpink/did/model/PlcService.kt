package social.aceinpink.did.model

import kotlinx.serialization.Serializable
import social.aceinpink.ServerConfig

@Serializable
data class PlcService(
    val type: String,
    val endpoint: String,
) {
    companion object {
        fun pds() = PlcService("AtprotoPersonalDataServer", ServerConfig.BASE_URL)
    }
}