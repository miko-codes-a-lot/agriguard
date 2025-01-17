package com.example.agriguard.modules.shared.ext

import io.realm.kotlin.types.RealmInstant
import org.mindrot.jbcrypt.BCrypt
import org.mongodb.kbson.BsonObjectId
import org.mongodb.kbson.ObjectId
import java.time.Instant
import java.time.format.DateTimeParseException

fun String?.toObjectId(): ObjectId {
    return if (!this.isNullOrEmpty()) {
        ObjectId(this)
    } else {
        ObjectId()
    }
}

fun String.toInstant(): Instant? {
    return try {
        Instant.parse(this)
    } catch (e: DateTimeParseException) {
        null
    }
}

fun String.toRealmInstant(): RealmInstant {
    val instant = Instant.parse(this)
    return RealmInstant.from(instant.epochSecond, instant.nano)
}

fun String?.toRealmInstantNullable(): RealmInstant? {
    return if (!this.isNullOrEmpty()) {
        val instant = Instant.parse(this)
        return RealmInstant.from(instant.epochSecond, instant.nano)
    } else {
        null
    }
}

fun String.hashPassword(): String {
    return BCrypt.hashpw(this, BCrypt.gensalt())
}

fun String.verifyPassword(password: String): Boolean {
    return BCrypt.checkpw(password, this)
}