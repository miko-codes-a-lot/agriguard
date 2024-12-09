package com.example.agriguard.modules.main.user.model.entity

import io.realm.kotlin.types.RealmInstant
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class User : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var firstName: String = ""
    var middleName: String? = ""
    var lastName: String = ""
    var mobileNumber: String? = ""
    var address: String? = ""
    var email: String = ""
    var dateOfBirth: RealmInstant = RealmInstant.now()
    var password: String = ""
    var nameOfSpouse: String? = ""
    var isAdmin: Boolean = false
    var isTechnician: Boolean = false
    var isFarmers: Boolean = false
    var userProfile: String? = null
    var validId: String? = null
    var createdById: ObjectId? = null
    var createdAt: RealmInstant = RealmInstant.now()
    var lastUpdatedById: ObjectId? = null
    var lastUpdatedAt: RealmInstant = RealmInstant.now()
    var deletedById: ObjectId? = null
    var deletedAt: RealmInstant? = null
}