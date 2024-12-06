package com.example.agriguard.modules.main.user.model.entity

import io.realm.kotlin.types.RealmInstant
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class Indemnity: RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var userId: String = ""
    var fillupdate: RealmInstant = RealmInstant.now()
    var regular: String? = ""
    var punla: String? = ""
    var cooperativeRice: String? = ""
    var rsbsa: String? = ""
    var sikat: String? = ""
    var apcpc: String? = ""
    var others: String? = ""
    var causeOfDamage: String? = ""
    var dateOfLoss: String? = ""
    var ageCultivation: String? = ""
    var areaDamaged: String? = ""
    var degreeOfDamage: String? = ""
    var expectedDateOfHarvest: String? = ""
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
    var createdById: ObjectId? = null
    var createdAt: RealmInstant = RealmInstant.now()
    var lastUpdatedById: ObjectId? = null
    var lastUpdatedAt: RealmInstant = RealmInstant.now()
    var deletedById: ObjectId? = null
    var deletedAt: RealmInstant? = null
}