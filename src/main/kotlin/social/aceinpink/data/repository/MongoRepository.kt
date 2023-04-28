package social.aceinpink.data.repository

import com.mongodb.reactivestreams.client.MongoDatabase
import social.aceinpink.data.MongoDB

abstract class MongoRepository {

    protected val database: MongoDatabase get() = MongoDB.database

}