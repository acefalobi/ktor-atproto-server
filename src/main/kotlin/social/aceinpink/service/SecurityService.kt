package social.aceinpink.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import social.aceinpink.security.JWT_SECRET
import java.util.Date

object SecurityService {

    private const val TOKEN_DURATION = 60000L

    fun createJWT(identifier: String): String = JWT.create()
        .withSubject(identifier)
        .withExpiresAt(Date(System.currentTimeMillis() + TOKEN_DURATION))
        .sign(Algorithm.HMAC512(JWT_SECRET.toByteArray()))

}