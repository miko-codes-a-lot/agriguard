package com.example.agriguard.modules.main.complain.service

import com.example.agriguard.modules.main.complain.mapper.toDTO
import com.example.agriguard.modules.main.complain.mapper.toEntity
import com.example.agriguard.modules.main.complain.model.dto.ComplaintInsuranceDto
import com.example.agriguard.modules.main.complain.model.entity.ComplaintInsurance
import com.example.agriguard.modules.main.user.model.dto.UserDto
import com.example.agriguard.modules.shared.ext.toInstantString
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.types.RealmInstant
import javax.inject.Inject

class ComplaintService @Inject constructor(private val realm: Realm) {
    suspend fun upsertComplaint(data: ComplaintInsuranceDto, currentUser: UserDto): Result<ComplaintInsuranceDto> {
        val dateNow = RealmInstant.now().toInstantString()
        if (data.id == null) {
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

    fun fetchListComplaintInsurance(userId: String): List<ComplaintInsuranceDto> {
        return realm.query<ComplaintInsurance>("userId == $0", userId)
            .find()
            .map { it.toDTO() }
    }

    fun fetchUserComplaintInsurance(userId: String): ComplaintInsuranceDto? {
        val result = realm.query<ComplaintInsurance>("userId == $0", userId)
            .find()
            .firstOrNull()
        return result?.toDTO()
    }
}