package social.aceinpink

import io.ktor.server.application.*

object ServerConfig {
    // Base
    var BASE_URL = ""
        private set

    // Database
    var MONGO_URI = ""
        private set
    var MONGO_DATABASE = ""
        private set

    // JWT
    var JWT_SECRET = ""

    fun Application.applyConfig() {
        BASE_URL = environment.config.property("base.url").getString()

        MONGO_URI = environment.config.property("mongodb.uri").getString()
        MONGO_DATABASE = environment.config.property("mongodb.database").getString()

        JWT_SECRET = environment.config.property("jwt.secret").getString()
    }

}