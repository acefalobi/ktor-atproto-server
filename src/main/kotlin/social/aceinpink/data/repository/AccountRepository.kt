package social.aceinpink.data.repository

import com.mongodb.client.model.IndexOptions
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.runBlocking
import org.bson.BsonDocument
import org.bson.BsonInt32
import org.litote.kmongo.coroutine.toList
import org.litote.kmongo.eq
import social.aceinpink.data.entity.Account
import social.aceinpink.exception.ResponseError
import social.aceinpink.exception.ResponseException

object AccountRepository : MongoRepository() {

    private val accountCollection = database.getCollection("accounts", Account::class.java)

    init {
        runBlocking {
            accountCollection.createIndex(
                BsonDocument("did", BsonInt32(1)),
                IndexOptions().unique(true)
            ).awaitSingle()
        }
    }

    suspend fun createAccount(account: Account): Account {
        val result = accountCollection
            .insertOne(account)
            .awaitSingle()
        if (!result.wasAcknowledged()) {
            throw ResponseException(ResponseError.UnknownError, "There was an error creating an account")
        }
        return findAccountByDid(account.did) ?: throw ResponseException(ResponseError.UnknownError)
    }

    suspend fun findAccountByEmail(email: String): Account? {
        return accountCollection.find(Account::email eq email).awaitFirstOrNull()
    }

    suspend fun findAccountByHandle(handle: String): Account? {
        return accountCollection.find(Account::handle eq handle).awaitFirstOrNull()
    }

    suspend fun findAccountByDid(did: String): Account? {
        return accountCollection.find(Account::did eq did).awaitFirstOrNull()
    }

    suspend fun getAllAccounts() = accountCollection.find().toList()

}