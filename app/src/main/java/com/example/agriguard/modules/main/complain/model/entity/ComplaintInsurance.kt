package com.example.agriguard.modules.main.complain.model.entity

import io.realm.kotlin.types.RealmInstant
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class ComplaintInsurance: RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var userId: String = ""
    var rice: Boolean = false
    var onion: Boolean = false
    var causeOfDamage: String? = ""
    var imageBase64: String? = null
    var status: String? = "pending"
    var createdById: ObjectId? = null
    var createdAt: RealmInstant = RealmInstant.now()
    var reviewById: ObjectId? = null
    var lastUpdatedById: ObjectId? = null
    var lastUpdatedAt: RealmInstant = RealmInstant.now()
    var deletedById: ObjectId? = null
    var deletedAt: RealmInstant? = null

}