package com.example.agriguard.modules.main.indemnity.model.mapper

import com.example.agriguard.modules.main.indemnity.model.dto.IndemnityDto
import com.example.agriguard.modules.main.indemnity.model.entity.Indemnity
import com.example.agriguard.modules.shared.ext.toInstantString
import com.example.agriguard.modules.shared.ext.toObjectId
import com.example.agriguard.modules.shared.ext.toRealmInstant
import com.example.agriguard.modules.shared.ext.toRealmInstantNullable
import io.realm.kotlin.types.RealmInstant

fun Indemnity.toDTO(): IndemnityDto {
    return IndemnityDto(
        id = _id.toHexString(),
        userId = userId,
        fillUpdate = fillUpDate.toInstantString(),
        regular = regular,
        punla = punla,
        cooperativeRice = cooperativeRice,
        rsbsa = rsbsa,
        sikat = sikat,
        apcpc = apcpc,
        others = others,
        causeOfDamage = causeOfDamage,
        dateOfLoss = dateOfLoss.toInstantString(),
        ageCultivation = ageCultivation,
        areaDamaged = areaDamaged,
        degreeOfDamage = degreeOfDamage,
        expectedDateOfHarvest = expectedDateOfHarvest.toInstantString(),
        north = north,
        south = south,
        east = east,
        west = west,
        upaSaGawaBilang = upaSaGawaBilang,
        upaSaGawaHalaga = upaSaGawaHalaga,
        binhiBilang = binhiBilang,
        binhiHalaga = binhiHalaga,
        abonoBilang = abonoBilang,
        abonoHalaga = abonoHalaga,
        kemikalBilang = kemikalBilang,
        kemikalHalaga = kemikalHalaga,
        patubigBilang = patubigBilang,
        patubigHalaga = patubigHalaga,
        ibapaBilang = ibapaBilang,
        ibapaHalaga = ibapaHalaga,
        kabuuanBilang = kabuuanBilang,
        kabuuanHalaga = kabuuanHalaga,
        status = status,
        createdById = createdById?.toHexString(),
        createdAt = createdAt.toInstantString(),
        reviewById = reviewById?.toHexString(),
        lastUpdatedById = lastUpdatedById?.toHexString(),
        lastUpdatedAt = lastUpdatedAt.toInstantString(),
        deletedById = deletedById?.toHexString(),
        deletedAt = deletedAt?.toInstantString()
    )
}

fun IndemnityDto.toEntity(): Indemnity {
    val indemnityDto = this
    return Indemnity().apply {
        _id = indemnityDto.id.toObjectId()
        userId = indemnityDto.userId
        fillUpDate = indemnityDto.fillUpdate.toRealmInstant()
        regular = indemnityDto.regular
        punla = indemnityDto.punla
        cooperativeRice = indemnityDto.cooperativeRice
        rsbsa = indemnityDto.rsbsa
        sikat = indemnityDto.sikat
        apcpc = indemnityDto.apcpc
        others = indemnityDto.others
        causeOfDamage = indemnityDto.causeOfDamage
        dateOfLoss = indemnityDto.dateOfLoss.toRealmInstant()
        ageCultivation = indemnityDto.ageCultivation
        areaDamaged = indemnityDto.areaDamaged
        degreeOfDamage = indemnityDto.degreeOfDamage
        expectedDateOfHarvest = indemnityDto.expectedDateOfHarvest.toRealmInstant()
        north = indemnityDto.north
        south = indemnityDto.south
        east = indemnityDto.east
        west = indemnityDto.west
        upaSaGawaBilang = indemnityDto.upaSaGawaBilang
        upaSaGawaHalaga = indemnityDto.upaSaGawaHalaga
        binhiBilang = indemnityDto.binhiBilang
        binhiHalaga = indemnityDto.binhiHalaga
        abonoBilang = indemnityDto.abonoBilang
        abonoHalaga = indemnityDto.abonoHalaga
        kemikalBilang = indemnityDto.kemikalBilang
        kemikalHalaga = indemnityDto.kemikalHalaga
        patubigBilang = indemnityDto.patubigBilang
        patubigHalaga = indemnityDto.patubigHalaga
        ibapaBilang = indemnityDto.ibapaBilang
        ibapaHalaga = indemnityDto.ibapaHalaga
        kabuuanBilang = indemnityDto.kabuuanBilang
        kabuuanHalaga = indemnityDto.kabuuanHalaga
        status = indemnityDto.status
        createdById = indemnityDto.createdById?.toObjectId()
        createdAt = indemnityDto.createdAt.toRealmInstantNullable() ?: RealmInstant.now()
        reviewById = indemnityDto.reviewById?.toObjectId()
        lastUpdatedById = indemnityDto.lastUpdatedById?.toObjectId()
        lastUpdatedAt = indemnityDto.lastUpdatedAt.toRealmInstantNullable() ?: RealmInstant.now()
        deletedById = indemnityDto.deletedById?.toObjectId()
        deletedAt = indemnityDto.deletedAt.toRealmInstantNullable()
    }
}
