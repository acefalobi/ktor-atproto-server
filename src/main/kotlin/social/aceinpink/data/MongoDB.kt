package social.aceinpink.data

import com.mongodb.reactivestreams.client.MongoDatabase
import org.litote.kmongo.reactivestreams.KMongo
import social.aceinpink.ServerConfig

object MongoDB {

    private val client = KMongo.createClient(ServerConfig.MONGO_URI)
    val database: MongoDatabase = client.getDatabase(ServerConfig.MONGO_DATABASE)

}