package com.example.agriguard.modules.main.complain.mapper

import com.example.agriguard.modules.main.complain.model.dto.ComplaintInsuranceDto
import com.example.agriguard.modules.main.complain.model.entity.ComplaintInsurance
import com.example.agriguard.modules.main.onion.model.entity.OnionInsurance
import com.example.agriguard.modules.shared.ext.toInstantString
import com.example.agriguard.modules.shared.ext.toObjectId
import com.example.agriguard.modules.shared.ext.toRealmInstantNullable
import io.realm.kotlin.types.RealmInstant

fun ComplaintInsurance.toDTO(): ComplaintInsuranceDto {
    return ComplaintInsuranceDto(
        id = _id.toHexString(),
        userId = userId,
        rice = rice,
        onion = onion,
        causeOfDamage = causeOfDamage,
        treatment = treatment,
        imageBase64 = imageBase64,
        createdById = createdById?.toHexString(),
        createdAt = createdAt.toInstantString(),
        lastUpdatedById = lastUpdatedById?.toHexString(),
        lastUpdatedAt = lastUpdatedAt.toInstantString(),
        deletedById = deletedById?.toHexString(),
    )
}

fun ComplaintInsuranceDto.toEntity(): ComplaintInsurance {
    val complaint = this
    return ComplaintInsurance().apply {
        _id = complaint.id.toObjectId()
        userId = complaint.userId
        rice = complaint.rice
        onion = complaint.onion
        causeOfDamage = complaint.causeOfDamage
        treatment = complaint.treatment
        imageBase64 = complaint.imageBase64
        createdAt = complaint.createdAt.toRealmInstantNullable() ?: RealmInstant.now()
        lastUpdatedById = complaint.lastUpdatedById?.toObjectId()
        lastUpdatedAt = complaint.lastUpdatedAt.toRealmInstantNullable() ?: RealmInstant.now()
        deletedById = complaint.deletedById?.toObjectId()
        deletedAt = complaint.deletedAt.toRealmInstantNullable()
    }
}