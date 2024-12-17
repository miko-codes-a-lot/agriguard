package com.example.agriguard.modules.main.complain.mapper

import com.example.agriguard.modules.main.complain.model.dto.ComplaintInsuranceDto
import com.example.agriguard.modules.main.complain.model.entity.ComplaintInsurance
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
        variety = variety,
        areaDamage = areaDamage,
        degreeOfDamage = degreeOfDamage,
        causeOfDamage = causeOfDamage,
        imageBase64 = imageBase64,
        status = status,
        createdById = createdById?.toHexString(),
        reviewById = reviewById?.toHexString(),
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
        variety = complaint.variety
        areaDamage = complaint.areaDamage
        degreeOfDamage = complaint.degreeOfDamage
        causeOfDamage = complaint.causeOfDamage
        imageBase64 = complaint.imageBase64
        status = complaint.status
        createdAt = complaint.createdAt.toRealmInstantNullable() ?: RealmInstant.now()
        createdById = complaint.createdById?.toObjectId()
        reviewById = complaint.reviewById?.toObjectId()
        lastUpdatedById = complaint.lastUpdatedById?.toObjectId()
        lastUpdatedAt = complaint.lastUpdatedAt.toRealmInstantNullable() ?: RealmInstant.now()
        deletedById = complaint.deletedById?.toObjectId()
        deletedAt = complaint.deletedAt.toRealmInstantNullable()
    }
}