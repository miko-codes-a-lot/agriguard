package com.example.agriguard.modules.main.user.model.mapper

import com.example.agriguard.modules.main.user.model.dto.AddressDto
import com.example.agriguard.modules.main.user.model.entity.Address
import com.example.agriguard.modules.shared.ext.toObjectId


fun Address.toDto(): AddressDto {
    return AddressDto(
        id = _id.toHexString(),
        name = name,
    )
}

fun AddressDto.toEntity(): Address {
    val userDto = this
    return Address().apply {
        _id = userDto.id.toObjectId()
        name = userDto.name
    }
}