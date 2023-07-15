package social.aceinpink.did

import com.identityfoundry.ddi.protocol.multicodec.Multicodec
import com.identityfoundry.ddi.protocol.multicodec.MulticodecEncoder
import org.bouncycastle.crypto.AsymmetricCipherKeyPair
import org.bouncycastle.crypto.generators.ECKeyPairGenerator
import org.bouncycastle.crypto.generators.Ed25519KeyPairGenerator
import org.bouncycastle.crypto.params.ECDomainParameters
import org.bouncycastle.crypto.params.ECKeyGenerationParameters
import org.bouncycastle.crypto.params.ECPrivateKeyParameters
import org.bouncycastle.crypto.params.ECPublicKeyParameters
import org.bouncycastle.crypto.params.Ed25519KeyGenerationParameters
import org.bouncycastle.crypto.params.Ed25519PrivateKeyParameters
import org.bouncycastle.crypto.params.Ed25519PublicKeyParameters
import org.bouncycastle.crypto.signers.ECDSASigner
import org.bouncycastle.jce.ECNamedCurveTable
import org.bouncycastle.math.ec.ECPoint
import social.aceinpink.multibase.MultiBase
import social.aceinpink.security.sha256
import java.io.ByteArrayOutputStream
import java.io.File
import java.math.BigInteger
import java.security.SecureRandom
import java.security.SignatureException
import java.util.Arrays


private val keyPairGenerator = Ed25519KeyPairGenerator().apply {
    init(Ed25519KeyGenerationParameters(SecureRandom()))
}

private val secp256k1curve = ECNamedCurveTable.getParameterSpec("secp256k1")
private val secp256k1Params = ECDomainParameters(secp256k1curve.curve, secp256k1curve.g, secp256k1curve.n, secp256k1curve.h, secp256k1curve.seed)
private val secp256k1Generator = ECKeyPairGenerator().apply {
    val secureRandom = SecureRandom()

    init(ECKeyGenerationParameters(secp256k1Params, secureRandom))
}

fun main() {
//    val keyPairGenerator = Ed25519KeyPairGenerator()
//    keyPairGenerator.init(Ed25519KeyGenerationParameters(SecureRandom()))
//    val keyPair = keyPairGenerator.generateKeyPair()
//    val privateKey = keyPair.private as Ed25519PrivateKeyParameters
//    val publicKey = keyPair.public as Ed25519PublicKeyParameters
//
//
//    val multicodecPublicKey = MulticodecEncoder.encode(Multicodec.ED25519_PUB, publicKey.encoded)
//    val multibasePublicKey = MultiBase.encode(MultiBase.Base.BASE58_BTC, multicodecPublicKey)
//    println("private: $privateKey")
//    println("public: $multibasePublicKey")


//    val secureRandom = SecureRandom()
//    val curve = ECNamedCurveTable.getParameterSpec("secp256k1")
//    val keyPair = keyPairGenerator.generateKeyPair()
//    ECKeyPairGenerator().apply {
//        init(
//            ECKeyGenerationParameters(
//                ECDomainParameters(curve.curve, curve.g, curve.n),
//                secureRandom
//            )
//        )
//    }

    val signingKeyFile = File("server_signing_key")
    val signingKeyPointQ = secp256k1curve.g.multiply(BigInteger(1, signingKeyFile.readBytes()))
//    val signingPrivateKey = Ed25519PrivateKeyParameters(signingKeyFile.readBytes())
//    val signingPublicKey = ECPublicKeyParameters(pointQ, secp256k1Params)

    val rotationKeyFile = File("server_rotation_key")
    val rotationKeyPointD = BigInteger(rotationKeyFile.readBytes())
    val rotationPrivateKey = ECPrivateKeyParameters(rotationKeyPointD, secp256k1Params)
    val rotationKeyPointQ = secp256k1curve.g.multiply(rotationKeyPointD)
    val rotationPublicKey = ECPublicKeyParameters(rotationKeyPointQ, secp256k1Params)

    val recoveryKeyFile = File("server_recovery_key")
    val recoveryKeyPointQ = secp256k1curve.g.multiply(BigInteger(1, recoveryKeyFile.readBytes()))
//    val recoveryPublicKey = recoveryPrivateKey.generatePublicKey()

//    val createOp = Operations.create(
//        signingKey = generateDidKey(signingPublicKey),
//        signer = Ed25519Signer().apply { init(true, rotationPrivateKey) },
//        handle = "ace",
//        listOf(generateDidKey(recoveryPublicKey), generateDidKey(rotationPublicKey))
//    )
    val signer = ECDSASigner().apply { init(true, rotationPrivateKey) }
    val verifier = ECDSASigner().apply { init(false, rotationPublicKey) }
    val message = "Hello World".toByteArray()
    var sig: Array<BigInteger>
    val signedMessage = signer.run {
        val signature = generateSignature(sha256(message))
        sig = signature
        val output = ByteArrayOutputStream()
        signature.forEach {
            output.writeBytes(it.toByteArray())
        }
        output.toByteArray()
    }
    val decodedSignedMessage = decodeSignature(signedMessage)
    signer.init(false, rotationPublicKey)
    println(signer.verifySignature(signedMessage, decodedSignedMessage[0], decodedSignedMessage[1]))


//    val createOp = Operations.create(
//        signingKey = generateDidKey(signingKeyPointQ),
//        signer = ECDSASigner().apply { init(true, rotationPrivateKey) },
//        handle = "ace",
//        listOf(generateDidKey(recoveryKeyPointQ), generateDidKey(rotationKeyPointQ))
//    )
//    println("Did: ${createOp.did}")
//    println("Op: ${createOp.operation}")
//    val privateKey = keyPairGenerator.generateKeyPair().private as Ed25519PrivateKeyParameters
//    File("server_recovery_key").writeBytes(privateKey.encoded)

//    val privateKey = secp256k1Generator.generateKeyPair().private as ECPrivateKeyParameters
//    val publicKey = secp256k1Generator.generateKeyPair().public as ECPublicKeyParameters
//
//    File("server_signing_key").writeBytes(privateKey.d.toByteArray())

//    secp256k1curve.g.multiply(BigInteger(privateKey.d.toByteArray()))
//    privateKey.d.toByteArray()
//    publicKey.q.getEncoded(false)
//    println(privateKey)
}

private fun ECDSASigner.sign(data: ByteArray): ByteArray {
    val signature = generateSignature(data)

    // Convert the signature to byte arrays
    val r = signature[0].toByteArray(32)
    val s = signature[1].toByteArray(32)

    val combinedSignature = ByteArray(64)
    System.arraycopy(r, r.size - 32, combinedSignature, 0, 32)
    System.arraycopy(s, s.size - 32, combinedSignature, 32, 32)

    return combinedSignature
}

private fun BigInteger.toByteArray(size: Int): ByteArray {
    val result = ByteArray(size)
    val byteArray = toByteArray()
    val start = if (byteArray.size == size + 1) 1 else 0
    val length = byteArray.size.coerceAtMost(size)
    System.arraycopy(byteArray, start, result, size - length, length)
    return result
}

private fun generateDidKey(publicKey: Ed25519PublicKeyParameters): String {
    val multicodecPublicKey = MulticodecEncoder.encode(Multicodec.ED25519_PUB, publicKey.encoded)
    val multibasePublicKey = MultiBase.encode(MultiBase.Base.BASE58_BTC, multicodecPublicKey)

    return "did:key:$multibasePublicKey"
}

private fun generateDidKey(q: ECPoint): String {
    val multicodecPublicKey = MulticodecEncoder.encode(Multicodec.SECP256K1_PUB, q.getEncoded(false))
    val multibasePublicKey = MultiBase.encode(MultiBase.Base.BASE58_BTC, multicodecPublicKey)

    return "did:key:$multibasePublicKey"
}

private fun generateDidKey(keyPair: AsymmetricCipherKeyPair): String {
    val privateKey = keyPair.private as Ed25519PrivateKeyParameters
    val publicKey = keyPair.public as Ed25519PublicKeyParameters
    Ed25519PrivateKeyParameters(byteArrayOf()).encoded
    privateKey.encoded

    MulticodecEncoder.decode(byteArrayOf()).codec
    val multicodecPublicKey = MulticodecEncoder.encode(Multicodec.ED25519_PUB, publicKey.encoded)
    val multibasePublicKey = MultiBase.encode(MultiBase.Base.BASE58_BTC, multicodecPublicKey)

    return "did:key:$multibasePublicKey"
}

// Decode a DER-encoded ECDSA signature and return the r and s values
fun decodeSignature(signature: ByteArray): Array<BigInteger> {
    // Parse the signature bytes into r/s and the selector value.
    // Parse the signature bytes into r/s and the selector value.
    if (signature.size < 65) throw SignatureException("Signature truncated, expected 65 bytes and got " + signature.size)

    val r = BigInteger(1, signature.copyOfRange(1, 33))
    val s = BigInteger(1, signature.copyOfRange(33, 65))
    return arrayOf(r, s)
}