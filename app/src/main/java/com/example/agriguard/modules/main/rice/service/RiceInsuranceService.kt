package com.example.agriguard.modules.main.rice.service

import com.example.agriguard.modules.main.rice.model.dto.RiceInsuranceDto
import com.example.agriguard.modules.main.user.model.dto.UserDto
import com.example.agriguard.modules.main.rice.mapper.toDTO
import com.example.agriguard.modules.main.rice.mapper.toEntity
import com.example.agriguard.modules.shared.ext.toInstantString
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.types.RealmInstant
import javax.inject.Inject

class RiceInsuranceService @Inject constructor(private val realm: Realm)  {
    suspend fun upsert(data: RiceInsuranceDto, currentUser: UserDto): Result<RiceInsuranceDto> {
        val dateNow = RealmInstant.now().toInstantString()
        if (data.id != null) {
            data.userId = currentUser.id!!
            data.createdById = currentUser.id!!
            data.createdAt = dateNow
        }
        data.lastUpdatedById = currentUser.id!!
        data.lastUpdatedAt = dateNow

        return try {
            realm.write {
                val entity = copyToRealm(data.toEntity(), updatePolicy = UpdatePolicy.ALL)
                Result.success(entity.toDTO())
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}