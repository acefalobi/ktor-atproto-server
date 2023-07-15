package social.aceinpink.did.operation

import org.bouncycastle.asn1.ocsp.CertID
import org.bouncycastle.crypto.Signer
import org.bouncycastle.crypto.signers.ECDSASigner
import social.aceinpink.did.DidUtil
import social.aceinpink.did.model.CreateOpResult
import social.aceinpink.did.model.PlcService
import kotlin.math.sign

object Operations {
    private fun formatOperation(
        signingKey: String,
        handle: String,
        rotationKeys: List<String>,
        prev: Nothing?,
    ): PlcOperation {
        return PlcOperation(
            verificationMethods = mapOf("atproto" to signingKey),
            alsoKnownAs = listOf("at://$handle"),
            services = mapOf("atproto_pds" to PlcService.pds()),
            rotationKeys = rotationKeys,
            prev = prev,
        )
    }

    private fun atproto(
        signer: Signer,
        signingKey: String,
        handle: String,
        rotationKeys: List<String>,
        prev: Nothing?,
    ): SignedPlcOperation {
        return formatOperation(signingKey, handle, rotationKeys, prev).signed(signer)
    }

    private fun atproto(
        signer: ECDSASigner,
        signingKey: String,
        handle: String,
        rotationKeys: List<String>,
        prev: Nothing?,
    ): SignedPlcOperation {
        return formatOperation(signingKey, handle, rotationKeys, prev).signed(signer)
    }

    fun create(
        signingKey: String,
        signer: Signer,
        handle: String,
        rotationKeys: List<String>,
    ): CreateOpResult {
        val operation = atproto(signer, signingKey, handle, rotationKeys, null)
        val did = DidUtil.createDidFromOperation(operation)

        return CreateOpResult(operation, did)
    }

    fun create(
        signingKey: String,
        signer: ECDSASigner,
        handle: String,
        rotationKeys: List<String>,
    ): CreateOpResult {
        val operation = atproto(signer, signingKey, handle, rotationKeys, null)
        val did = DidUtil.createDidFromOperation(operation)

        return CreateOpResult(operation, did)
    }

}