package com.example.agriguard.modules.main.user.service

import com.example.agriguard.modules.main.user.model.dto.AddressDto
import com.example.agriguard.modules.main.user.model.entity.Address
import com.example.agriguard.modules.main.user.model.mapper.toDto
import com.example.agriguard.modules.main.user.model.mapper.toEntity
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import org.mongodb.kbson.ObjectId
import javax.inject.Inject

class AddressService @Inject constructor(private val realm: Realm) {

    fun fetchOne(addressId: ObjectId): AddressDto {
        return realm.query<Address>("_id == $0", addressId)
            .find()
            .first()
            .run { toDto() }
    }

    fun fetch(): List<AddressDto> {
        return realm.query<Address>()
            .find()
            .map { address -> address.toDto() }
    }

    suspend fun upsert(data: AddressDto): Result<AddressDto> {
        return try {
            realm.write {
                val address = copyToRealm(data.toEntity(), updatePolicy = UpdatePolicy.ALL)
                Result.success(address.toDto());
            }
        } catch (error: Exception) {
            Result.failure(error)
        }
    }
}