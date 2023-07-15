package social.aceinpink.did

import kotlinx.serialization.encodeToByteArray
import social.aceinpink.did.operation.SignedPlcOperation
import social.aceinpink.multibase.MultiBase
import social.aceinpink.util.cbor
import java.security.MessageDigest


object DidUtil {

    fun createDidFromOperation(operation: SignedPlcOperation): String {
        val sha256 = MessageDigest.getInstance("SHA-256")
        val sha256Hash = sha256.digest(cbor.encodeToByteArray(operation))
        val multibaseHash = MultiBase.encode(MultiBase.Base.BASE32, sha256Hash).take(24)

        return "dlc:plc:$multibaseHash"
    }

}