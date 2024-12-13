package com.example.agriguard.modules.main.rice.model.entity

import io.realm.kotlin.types.RealmInstant
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class RiceInsurance: RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var userId: String = ""
    var insuranceId: String? = ""
    var lender: String? = ""
    var fillUpDate: RealmInstant = RealmInstant.now()
    var new: Boolean = false
    var renewal: Boolean = false
    var selfFinanced: Boolean = false
    var borrowing: Boolean = false
    var ipTribe: String? = ""
    var street: String? = ""
    var barangay: String? = ""
    var municipality: String? = ""
    var province: String? = ""
    var bankName: String? = ""
    var bankAddress: String? = ""
    var male: Boolean = false
    var female: Boolean = false
    var single: Boolean = false
    var married: Boolean = false
    var widow: Boolean = false
    var nameOfBeneficiary: String? = ""
    var age: String? = ""
    var relationship: String? = ""
    var sitio: String? = ""
    var farmLocationBarangay: String? = ""
    var farmLocationMunicipality: String? = ""
    var farmLocationProvince: String? = ""
    var north: String? = ""
    var south: String? = ""
    var east: String? = ""
    var west: String? = ""
    var variety: String? = ""
    var platingMethod: String? = ""
    var dateOfSowing: RealmInstant = RealmInstant.now()
    var dateOfPlanting: RealmInstant = RealmInstant.now()
    var dateOfHarvest: RealmInstant = RealmInstant.now()
    var landOfCategory: String? = ""
    var soilTypes: String? = ""
    var topography: String? = ""
    var sourceOfIrrigations: String? = ""
    var tenurialStatus: String? = ""
    var rice: Boolean = false
    var multiRisk: Boolean = false
    var natural: Boolean = false
    var amountOfCover: String? = ""
    var premium: String? = ""
    var cltiAdss: String? = ""
    var sumInsured: String? = ""
    var wet: Boolean = false
    var dry: Boolean = false
    var cicNo: String? = ""
    var dateCicIssued: RealmInstant = RealmInstant.now()
    var cocNo: String? = ""
    var dateCocIssued: RealmInstant = RealmInstant.now()
    var periodOfCover: String? = ""
    var from: String? = ""
    var to: String? = ""
    var status: String? = "pending"
    var createdById: ObjectId? = null
    var reviewById: ObjectId? = null
    var createdAt: RealmInstant = RealmInstant.now()
    var lastUpdatedById: ObjectId? = null
    var lastUpdatedAt: RealmInstant = RealmInstant.now()
    var deletedById: ObjectId? = null
    var deletedAt: RealmInstant? = null
}