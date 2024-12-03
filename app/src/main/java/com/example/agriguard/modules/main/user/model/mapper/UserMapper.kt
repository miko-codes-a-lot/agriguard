package com.example.agriguard.modules.main.user.model.mapper

import com.example.agriguard.modules.main.user.model.dto.UserDto
import com.example.agriguard.modules.main.user.model.entity.User
import com.example.agriguard.modules.shared.ext.toInstantString
import com.example.agriguard.modules.shared.ext.toInstantStringNullable
import com.example.agriguard.modules.shared.ext.toObjectId
import com.example.agriguard.modules.shared.ext.toRealmInstant
import com.example.agriguard.modules.shared.ext.toRealmInstantNullable
import io.realm.kotlin.types.RealmInstant

fun User.toDTO(): UserDto {
    return UserDto(
        id = _id.toHexString(),
        firstName = firstName,
        middleName = middleName,
        lastName = lastName,
        mobileNumber = mobileNumber,
        address = address,
        email = email,
        dateOfBirth = dateOfBirth.toInstantString(),
        password = password,
        isAdmin = isAdmin,
        isTechnician = isTechnician,
        isFarmers = isFarmers,
        userProfile = userProfile,
        validId = validId,
        createdById = createdById?.toHexString(),
        createdAt = createdAt.toInstantStringNullable(),
        lastUpdatedById = lastUpdatedById?.toHexString(),
        lastUpdatedAt = lastUpdatedAt.toInstantStringNullable(),
        deletedById = deletedById?.toHexString(),
        deletedAt = deletedAt?.run { this.toInstantStringNullable() },
    )
}

fun UserDto.toEntity(): User {
    val userDto = this
    return User().apply {
        _id = userDto.id.toObjectId()
        firstName = userDto.firstName
        middleName = userDto.middleName
        lastName = userDto.lastName
        mobileNumber = userDto.mobileNumber
        address = userDto.address
        email = userDto.email
        dateOfBirth = userDto.dateOfBirth.toRealmInstant()
        password = userDto.password
        isAdmin = userDto.isAdmin
        isTechnician = userDto.isTechnician
        isFarmers = userDto.isFarmers
        userProfile = userDto.userProfile
        validId = userDto.validId
        createdById = userDto.createdById?.toObjectId()
        createdAt = userDto.createdAt.toRealmInstantNullable() ?: RealmInstant.now()
        lastUpdatedById = userDto.lastUpdatedById?.toObjectId()
        lastUpdatedAt = userDto.createdAt.toRealmInstantNullable() ?: RealmInstant.now()
        deletedById = userDto.deletedById?.toObjectId()
        deletedAt = userDto.deletedAt.toRealmInstantNullable()
    }
}
