package social.aceinpink.did.operation


import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToByteArray
import org.bouncycastle.crypto.Signer
import org.bouncycastle.crypto.digests.SHA256Digest
import org.bouncycastle.crypto.signers.ECDSASigner
import org.litote.kmongo.out
import social.aceinpink.did.model.PlcService
import social.aceinpink.multibase.MultiBase
import social.aceinpink.security.sha256
import social.aceinpink.util.cbor
import java.io.ByteArrayOutputStream

@Serializable
open class PlcOperation(
    val verificationMethods: Map<String, String>,
    val alsoKnownAs: List<String>,
    val services: Map<String, PlcService>,
    override val rotationKeys: List<String>,
    override val prev: Nothing?,
) : Operation {
    val type = "plc_operation"

    constructor(plcOperation: PlcOperation) : this(
        plcOperation.verificationMethods,
        plcOperation.alsoKnownAs,
        plcOperation.services,
        plcOperation.rotationKeys,
        plcOperation.prev,
    )

    /**
     * Signs a PLC operation with a [signer]
     *
     * Make sure to call [Signer.init] before calling this.
     */
    fun signed(signer: Signer): SignedPlcOperation {
        val data = sha256(cbor.encodeToByteArray(this))
        val signedData = signer.run {
            update(data, 0, data.size)
            generateSignature()
        }
        return SignedPlcOperation(
            plcOperation = this,
            sig = MultiBase.encode(MultiBase.Base.BASE64_URL, signedData)
        )
    }

    /**
     * Signs a PLC operation with a [signer]
     *
     * Make sure to call [ECDSASigner.init] before calling this.
     */
    fun signed(signer: ECDSASigner): SignedPlcOperation {
        val data = cbor.encodeToByteArray(this)
        val signedData = signer.run {
            val signature = generateSignature(sha256(data))
            val output = ByteArrayOutputStream()
            signature.forEach {
                output.writeBytes(it.toByteArray())
            }
            output.toByteArray()

        }
        return SignedPlcOperation(
            plcOperation = this,
            sig = MultiBase.encode(MultiBase.Base.BASE64_URL, signedData)
        )
    }
}

@Serializable
class SignedPlcOperation(val plcOperation: PlcOperation, val sig: String) : PlcOperation(plcOperation)