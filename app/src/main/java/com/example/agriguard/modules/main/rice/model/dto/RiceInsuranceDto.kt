package com.example.agriguard.modules.main.rice.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class RiceInsuranceDto (
    var id: String? = null,
    var userId: String = "",
    var insuranceId: String? = null,
    var lender: String? = null,
    var fillUpDate: String = "",
    var new: Boolean = false,
    var renewal: Boolean = false,
    var selfFinanced: Boolean = false,
    var borrowing: Boolean = false,
    var ipTribe: String? = null,
    var street: String? = null,
    var barangay: String? = null,
    var municipality: String? = null,
    var province: String? = null,
    var bankName: String? = null,
    var bankAddress: String? = null,
    var male: Boolean = false,
    var female: Boolean = false,
    var single: Boolean = false,
    var married: Boolean = false,
    var widow: Boolean = false,
    var nameOfBeneficiary: String? = null,
    var age: String? = null,
    var relationship: String? = null,
    var sitio: String? = null,
    var farmLocationBarangay: String? = null,
    var farmLocationMunicipality: String? = null,
    var farmLocationProvince: String? = null,
    var north: String? = null,
    var south: String? = null,
    var east: String? = null,
    var west: String? = null,
    var variety: String? = null,
    var platingMethod: String? = null,
    var dateOfSowing: String = "",
    var dateOfPlanting: String = "",
    var dateOfHarvest: String = "",
    var landOfCategory: String? = null,
    var soilTypes: String? = null,
    var topography: String? = null,
    var sourceOfIrrigations: String? = null,
    var tenurialStatus: String? = null,
    var rice: Boolean = false,
    var multiRisk: Boolean = false,
    var natural: Boolean = false,
    var amountOfCover: String? = null,
    var premium: String? = null,
    var cltiAdss: String? = null,
    var sumInsured: String? = null,
    var wet: Boolean = false,
    var dry: Boolean = false,
    var cicNo: String? = null,
    var dateCicIssued: String = "",
    var cocNo: String? = null,
    var dateCocIssued: String = "",
    var periodOfCover: String? = null,
    var from: String? = null,
    var to: String? = null,
    var status: String? = "pending",
    var createdById: String? = null,
    var createdAt: String? = null,
    var reviewById: String? = null,
    var lastUpdatedById: String? = null,
    var lastUpdatedAt: String? = null,
    var deletedById: String? = null,
    var deletedAt: String? = null,
)