package com.example.agriguard.modules.main.farmer.viewmodel

import androidx.lifecycle.ViewModel
import com.example.agriguard.modules.main.user.model.dto.AddressDto
import com.example.agriguard.modules.main.user.model.dto.UserDto
import com.example.agriguard.modules.main.user.service.AddressService
import com.example.agriguard.modules.main.user.service.UserService
import dagger.hilt.android.lifecycle.HiltViewModel
import org.mongodb.kbson.ObjectId
import javax.inject.Inject

@HiltViewModel
class FarmersViewModel @Inject  constructor(
    private val userService: UserService,
    private val addressService: AddressService
): ViewModel() {
    fun fetchAddresses(): List<AddressDto> {
        return addressService.fetch()
    }

    fun fetchUsers(
        userId: ObjectId,
        isAdmin: Boolean = false,
        addressName: String?,
    ):List<UserDto> {
        val id = if (!isAdmin) userId else null
        return userService.fetch(
            isFarmers = true,
            userId = id,
            addressName = addressName
        )
    }

    fun fetchOneAddress(addressId: ObjectId): AddressDto {
        return addressService.fetchOne(addressId)
    }

    suspend fun upsertAddress(addressDto: AddressDto): Result<AddressDto> {
        return addressService.upsert(addressDto)
    }

}