package com.example.agriguard.modules.main.onion.model.entity

import io.realm.kotlin.types.RealmInstant
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.BsonObjectId
import org.mongodb.kbson.ObjectId

class OnionInsurance:RealmObject {
    @PrimaryKey
    var _id: ObjectId = BsonObjectId()
    var userId: String = ""
    var fillUpDate: RealmInstant = RealmInstant.now()
    var ipTribe: String? = ""
    var male: Boolean = false
    var female: Boolean = false
    var single: Boolean = false
    var married: Boolean = false
    var widow: Boolean = false
    var nameOfBeneficiary: String? = ""
    var ageOfBeneficiary: String? = ""
    var relationshipOfBeneficiary: String? = ""
    var farmLocation: String? = ""
    var area: String? = ""
    var soilType: String? = ""
    var soilPh: String? = ""
    var topography: String? = ""
    var variety: String? = ""
    var dateOfPlanting: RealmInstant = RealmInstant.now()
    var estdDateOfHarvest: RealmInstant = RealmInstant.now()
    var ageGroup: String? = ""
    var noOfHills: String? = ""
    var typeOfIrrigation: String? = ""
    var averageYield: String? = ""
    var landPreparation: String? = ""
    var materialsItem: String? = ""
    var materialsQuantity: String? = ""
    var materialsCost: String? = ""
    var laborWorkForce: String? = ""
    var laborQuantity: String? = ""
    var laborCost: String? = ""
    var totalCoast: String? = ""
    var farmLocationSitio: String? = ""
    var farmLocatioBarangay: String? = ""
    var farmLocationMunicipality: String? = ""
    var farmLocationProvince: String? = ""
    var north: String? = ""
    var south: String? = ""
    var east: String? = ""
    var west: String? = ""
    var status: String? = "pending"
    var createdById: ObjectId? = null
    var createdAt: RealmInstant = RealmInstant.now()
    var lastUpdatedById: ObjectId? = null
    var lastUpdatedAt: RealmInstant = RealmInstant.now()
    var deletedById: ObjectId? = null
    var deletedAt: RealmInstant? = null
}