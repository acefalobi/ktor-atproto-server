package social.aceinpink.data.entity

import org.bson.codecs.pojo.annotations.BsonId
import java.util.Date
import java.util.UUID

abstract class Entity {
    @BsonId
    val _id: String = UUID.randomUUID().toString()

    val createdAt: Date = Date()
}