package com.example.agriguard.modules.main.indemnity.model.entity

import io.realm.kotlin.types.RealmInstant
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class Indemnity: RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var userId: String = ""
    var fillupdate: RealmInstant = RealmInstant.now()
    var regular: Boolean = false
    var punla: Boolean = false
    var cooperativeRice: Boolean = false
    var rsbsa: Boolean = false
    var sikat: Boolean = false
    var apcpc: Boolean = false
    var others: String? = ""
    var causeOfDamage: String? = ""
    var dateOfLoss: RealmInstant = RealmInstant.now()
    var ageCultivation: String? = ""
    var areaDamaged: String? = ""
    var degreeOfDamage: String? = ""
    var expectedDateOfHarvest: RealmInstant = RealmInstant.now()
    var north: String? = ""
    var south: String? = ""
    var east: String? = ""
    var west: String? = ""
    var upaSaGawaBilang: String? = ""
    var upaSaGawaHalaga: String? = ""
    var binhiBilang: String? = ""
    var binhiHalaga: String? = ""
    var abonoBilang: String? = ""
    var abonoHalaga: String? = ""
    var kemikalBilang: String? = ""
    var kemikalHalaga: String? = ""
    var patubigBilang: String? = ""
    var patubigHalaga: String? = ""
    var ibapaBilang: String? = ""
    var ibapaHalaga: String? = ""
    var kabuuanBilang: String? = ""
    var kabuuanHalaga: String? = ""
    var status: String? = "pending"
    var createdById: ObjectId? = null
    var createdAt: RealmInstant = RealmInstant.now()
    var reviewById: ObjectId? = null
    var lastUpdatedById: ObjectId? = null
    var lastUpdatedAt: RealmInstant = RealmInstant.now()
    var deletedById: ObjectId? = null
    var deletedAt: RealmInstant? = null
}