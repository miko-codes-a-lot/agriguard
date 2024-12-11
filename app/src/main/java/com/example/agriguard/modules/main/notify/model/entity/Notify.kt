package com.example.agriguard.modules.main.notify.model.entity

import io.realm.kotlin.types.RealmInstant
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.BsonObjectId
import org.mongodb.kbson.ObjectId

class Notify : RealmObject {
    companion object {
        const val COLLECTION_NAME = "Notify"
    }

    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var sender: ObjectId = ObjectId()
    var receiver: ObjectId = ObjectId()
    var documentId: ObjectId? = null
    var documentType: String? = null
    var message: String = ""
    var read: Boolean = false
    var createdAt: RealmInstant = RealmInstant.now()
}
