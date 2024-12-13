package com.example.agriguard.modules.main.rice.mapper

import com.example.agriguard.modules.main.rice.model.dto.RiceInsuranceDto
import com.example.agriguard.modules.main.rice.model.entity.RiceInsurance
import com.example.agriguard.modules.shared.ext.toInstantString
import com.example.agriguard.modules.shared.ext.toObjectId
import com.example.agriguard.modules.shared.ext.toRealmInstant
import com.example.agriguard.modules.shared.ext.toRealmInstantNullable
import io.realm.kotlin.types.RealmInstant

fun RiceInsurance.toDTO(): RiceInsuranceDto {
    return RiceInsuranceDto(
        id = _id.toHexString(),
        userId = userId,
        insuranceId = insuranceId,
        lender = lender,
        fillUpDate = fillUpDate.toInstantString(),
        new = new,
        renewal = renewal,
        selfFinanced = selfFinanced,
        borrowing = borrowing,
        ipTribe = ipTribe,
        street = street,
        barangay = barangay,
        municipality = municipality,
        province = province,
        bankName = bankName,
        bankAddress = bankAddress,
        male = male,
        female = female,
        single = single,
        married = married,
        widow = widow,
        nameOfBeneficiary = nameOfBeneficiary,
        age = age,
        relationship = relationship,
        sitio = sitio,
        farmLocationBarangay = farmLocationBarangay,
        farmLocationMunicipality = farmLocationMunicipality,
        farmLocationProvince = farmLocationProvince,
        north = north,
        south = south,
        east = east,
        west = west,
        variety = variety,
        platingMethod = platingMethod,
        dateOfSowing = dateOfSowing.toInstantString(),
        dateOfPlanting = dateOfPlanting.toInstantString(),
        dateOfHarvest = dateOfHarvest.toInstantString(),
        landOfCategory = landOfCategory,
        soilTypes = soilTypes,
        topography = topography,
        sourceOfIrrigations = sourceOfIrrigations,
        tenurialStatus = tenurialStatus,
        rice = rice,
        multiRisk = multiRisk,
        natural = natural,
        amountOfCover = amountOfCover,
        premium = premium,
        cltiAdss = cltiAdss,
        sumInsured = sumInsured,
        wet = wet,
        dry = dry,
        cicNo = cicNo,
        dateCicIssued = dateCicIssued.toInstantString(),
        cocNo = cocNo,
        dateCocIssued = dateCocIssued.toInstantString(),
        periodOfCover = periodOfCover,
        from = from,
        to = to,
        status = status,
        createdById = createdById?.toHexString(),
        reviewById =  reviewById?.toHexString(),
        createdAt = createdAt.toInstantString(),
        lastUpdatedById = lastUpdatedById?.toHexString(),
        lastUpdatedAt = lastUpdatedAt.toInstantString(),
        deletedById = deletedById?.toHexString(),
        deletedAt = deletedAt?.toInstantString(),
    )
}


fun RiceInsuranceDto.toEntity(): RiceInsurance {
    val riceInsuranceDto = this
    return RiceInsurance().apply {
        _id = riceInsuranceDto.id.toObjectId()
        userId = riceInsuranceDto.userId
        insuranceId = riceInsuranceDto.insuranceId
        lender = riceInsuranceDto.lender
        fillUpDate = riceInsuranceDto.fillUpDate.toRealmInstant()
        new = riceInsuranceDto.new
        renewal = riceInsuranceDto.renewal
        selfFinanced = riceInsuranceDto.selfFinanced
        borrowing = riceInsuranceDto.borrowing
        ipTribe = riceInsuranceDto.ipTribe
        street = riceInsuranceDto.street
        barangay = riceInsuranceDto.barangay
        municipality = riceInsuranceDto.municipality
        province = riceInsuranceDto.province
        bankName = riceInsuranceDto.bankName
        bankAddress = riceInsuranceDto.bankAddress
        male = riceInsuranceDto.male
        female = riceInsuranceDto.female
        single = riceInsuranceDto.single
        married = riceInsuranceDto.married
        widow = riceInsuranceDto.widow
        nameOfBeneficiary = riceInsuranceDto.nameOfBeneficiary
        age = riceInsuranceDto.age
        relationship = riceInsuranceDto.relationship
        sitio = riceInsuranceDto.sitio
        farmLocationBarangay = riceInsuranceDto.farmLocationBarangay
        farmLocationMunicipality = riceInsuranceDto.farmLocationMunicipality
        farmLocationProvince = riceInsuranceDto.farmLocationProvince
        north = riceInsuranceDto.north
        south = riceInsuranceDto.south
        east = riceInsuranceDto.east
        west = riceInsuranceDto.west
        variety = riceInsuranceDto.variety
        platingMethod = riceInsuranceDto.platingMethod
        dateOfSowing = riceInsuranceDto.dateOfSowing.toRealmInstant()
        dateOfPlanting = riceInsuranceDto.dateOfPlanting.toRealmInstant()
        dateOfHarvest = riceInsuranceDto.dateOfHarvest.toRealmInstant()
        landOfCategory = riceInsuranceDto.landOfCategory
        soilTypes = riceInsuranceDto.soilTypes
        topography = riceInsuranceDto.topography
        sourceOfIrrigations = riceInsuranceDto.sourceOfIrrigations
        tenurialStatus = riceInsuranceDto.tenurialStatus
        rice = riceInsuranceDto.rice
        multiRisk = riceInsuranceDto.multiRisk
        natural = riceInsuranceDto.natural
        amountOfCover = riceInsuranceDto.amountOfCover
        premium = riceInsuranceDto.premium
        cltiAdss = riceInsuranceDto.cltiAdss
        sumInsured = riceInsuranceDto.sumInsured
        wet = riceInsuranceDto.wet
        dry = riceInsuranceDto.dry
        cicNo = riceInsuranceDto.cicNo
        dateCicIssued = riceInsuranceDto.dateCicIssued.toRealmInstant()
        cocNo = riceInsuranceDto.cocNo
        dateCocIssued = riceInsuranceDto.dateCocIssued.toRealmInstant()
        periodOfCover = riceInsuranceDto.periodOfCover
        from = riceInsuranceDto.from
        to = riceInsuranceDto.to
        status = riceInsuranceDto.status
        createdById = riceInsuranceDto.createdById?.toObjectId()
        reviewById = riceInsuranceDto.reviewById?.toObjectId()
        createdAt = riceInsuranceDto.createdAt.toRealmInstantNullable() ?: RealmInstant.now()
        lastUpdatedById = riceInsuranceDto.lastUpdatedById?.toObjectId()
        lastUpdatedAt = riceInsuranceDto.lastUpdatedAt.toRealmInstantNullable() ?: RealmInstant.now()
        deletedById = riceInsuranceDto.deletedById?.toObjectId()
        deletedAt = riceInsuranceDto.deletedAt.toRealmInstantNullable()
    }
}
