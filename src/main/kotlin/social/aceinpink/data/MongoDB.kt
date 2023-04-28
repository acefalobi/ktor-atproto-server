package social.aceinpink.data

import com.mongodb.reactivestreams.client.MongoDatabase
import io.ktor.server.application.*
import org.litote.kmongo.reactivestreams.KMongo


private var MONGO_URI = ""
private var MONGO_DATABASE = ""

fun Application.configureDb() {
    MONGO_URI = environment.config.property("mongodb.uri").getString()
    MONGO_DATABASE = environment.config.property("mongodb.database").getString()
}

object MongoDB {

    private val client = KMongo.createClient(MONGO_URI)
    val database: MongoDatabase = client.getDatabase(MONGO_DATABASE)

}