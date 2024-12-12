package com.example.agriguard.modules.main.onion.mapper

import com.example.agriguard.modules.main.onion.model.dto.OnionInsuranceDto
import com.example.agriguard.modules.main.onion.model.entity.OnionInsurance
import com.example.agriguard.modules.shared.ext.toInstantString
import com.example.agriguard.modules.shared.ext.toObjectId
import com.example.agriguard.modules.shared.ext.toRealmInstant
import com.example.agriguard.modules.shared.ext.toRealmInstantNullable
import io.realm.kotlin.types.RealmInstant


fun OnionInsurance.toDTO(): OnionInsuranceDto {
    return OnionInsuranceDto(
        id = _id.toHexString(),
        userId = userId,
        fillUpDate = fillUpDate.toInstantString(),
        ipTribe = ipTribe,
        male = male,
        female = female,
        single = single,
        married = married,
        widow = widow,
        nameOfBeneficiary = nameOfBeneficiary,
        ageOfBeneficiary = ageOfBeneficiary,
        relationshipOfBeneficiary = relationshipOfBeneficiary,
        farmLocation = farmLocation,
        area = area,
        soilType = soilType,
        soilPh = soilPh,
        topography = topography,
        variety = variety,
        dateOfPlanting = dateOfPlanting.toInstantString(),
        estdDateOfHarvest = estdDateOfHarvest.toInstantString(),
        ageGroup = ageGroup,
        noOfHills = noOfHills,
        typeOfIrrigation = typeOfIrrigation,
        averageYield = averageYield,
        landPreparation = landPreparation,
        materialsItem = materialsItem,
        materialsQuantity = materialsQuantity,
        materialsCost = materialsCost,
        laborWorkForce = laborWorkForce,
        laborQuantity = laborQuantity,
        laborCost = laborCost,
        totalCoast = totalCoast,
        farmLocationSitio = farmLocationSitio,
        farmLocatioBarangay = farmLocatioBarangay,
        farmLocationMunicipality = farmLocationMunicipality,
        farmLocationProvince = farmLocationProvince,
        north = north,
        south = south,
        east = east,
        west = west,
        status = status,
        createdById = createdById?.toHexString(),
        createdAt = createdAt.toInstantString(),
        lastUpdatedById = lastUpdatedById?.toHexString(),
        lastUpdatedAt = lastUpdatedAt.toInstantString(),
        deletedById = deletedById?.toHexString(),
    )
}

fun OnionInsuranceDto.toEntity(): OnionInsurance {
    val onionInsurance = this
    return OnionInsurance().apply {
        _id = onionInsurance.id.toObjectId()
        userId = onionInsurance.userId
        fillUpDate = onionInsurance.fillUpDate.toRealmInstant()
        ipTribe = onionInsurance.ipTribe
        male = onionInsurance.male
        female = onionInsurance.female
        single = onionInsurance.single
        married = onionInsurance.married
        widow = onionInsurance.widow
        nameOfBeneficiary = onionInsurance.nameOfBeneficiary
        ageOfBeneficiary = onionInsurance.ageOfBeneficiary
        relationshipOfBeneficiary = onionInsurance.relationshipOfBeneficiary
        farmLocation = onionInsurance.farmLocation
        area = onionInsurance.area
        soilType = onionInsurance.soilType
        soilPh = onionInsurance.soilPh
        topography = onionInsurance.topography
        variety = onionInsurance.variety
        dateOfPlanting = onionInsurance.dateOfPlanting.toRealmInstant()
        estdDateOfHarvest = onionInsurance.estdDateOfHarvest.toRealmInstant()
        ageGroup = onionInsurance.ageGroup
        noOfHills = onionInsurance.noOfHills
        typeOfIrrigation = onionInsurance.typeOfIrrigation
        averageYield = onionInsurance.averageYield
        landPreparation = onionInsurance.landPreparation
        materialsItem = onionInsurance.materialsItem
        materialsQuantity = onionInsurance.materialsQuantity
        materialsCost = onionInsurance.materialsCost
        laborWorkForce = onionInsurance.laborWorkForce
        laborQuantity = onionInsurance.laborQuantity
        laborCost = onionInsurance.laborCost
        totalCoast = onionInsurance.totalCoast
        farmLocationSitio = onionInsurance.farmLocationSitio
        farmLocatioBarangay = onionInsurance.farmLocatioBarangay
        farmLocationMunicipality = onionInsurance.farmLocationMunicipality
        farmLocationProvince = onionInsurance.farmLocationProvince
        north = onionInsurance.north
        south = onionInsurance.south
        east = onionInsurance.east
        west = onionInsurance.west
        status = onionInsurance.status
        createdById = onionInsurance.createdById?.toObjectId()
        createdAt = onionInsurance.createdAt.toRealmInstantNullable() ?: RealmInstant.now()
        lastUpdatedById = onionInsurance.lastUpdatedById?.toObjectId()
        lastUpdatedAt = onionInsurance.lastUpdatedAt.toRealmInstantNullable() ?: RealmInstant.now()
        deletedById = onionInsurance.deletedById?.toObjectId()
        deletedAt = onionInsurance.deletedAt.toRealmInstantNullable()
    }
}