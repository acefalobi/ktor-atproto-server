package social.aceinpink.did.model

import social.aceinpink.did.operation.SignedPlcOperation

data class CreateOpResult(
    val operation: SignedPlcOperation,
    val did: String,
)
