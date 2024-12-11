package com.example.agriguard.modules.main.onion.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class OnionInsuranceDto (
    var id: String? = null,
    var userId: String = "",
    var fillUpDate: String = "",
    var ipTribe: String? = null,
    var male: Boolean = false,
    var female: Boolean = false,
    var single: Boolean = false,
    var married: Boolean = false,
    var widow: Boolean = false,
    var nameOfBeneficiary: String? = null,
    var ageOfBeneficiary: String? = null,
    var relationshipOfBeneficiary: String? = null,
    var farmLocation: String? = null,
    var area: String? = null,
    var soilType: String? = null,
    var soilPh: String? = null,
    var topography: String? = null,
    var variety: String? = null,
    var dateOfPlanting: String = "",
    var estdDateOfHarvest: String = "",
    var ageGroup: String? = null,
    var noOfHills: String? = null,
    var typeOfIrrigation: String? = null,
    var averageYield: String? = null,
    var landPreparation: String? = null,
    var materialsItem: String? = null,
    var materialsQuantity: String? = null,
    var materialsCost: String? = null,
    var laborWorkForce: String? = null,
    var laborQuantity: String? = null,
    var laborCost: String? = null,
    var totalCoast: String? = null,
    var farmLocationSitio: String? = null,
    var farmLocatioBarangay: String? = null,
    var farmLocationMunicipality: String? = null,
    var farmLocationProvince: String? = null,
    var north: String? = null,
    var south: String? = null,
    var east: String? = null,
    var west: String? = null,
    var createdById: String? = null,
    var createdAt: String? = null,
    var lastUpdatedById: String? = null,
    var lastUpdatedAt: String? = null,
    var deletedById: String? = null,
    var deletedAt: String? = null,
)