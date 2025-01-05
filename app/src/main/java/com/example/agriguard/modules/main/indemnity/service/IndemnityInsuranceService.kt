package com.example.agriguard.modules.main.indemnity.service

import android.util.Log
import com.example.agriguard.modules.main.indemnity.model.dto.IndemnityDto
import com.example.agriguard.modules.main.indemnity.model.dto.IndemnityWithUserDto
import com.example.agriguard.modules.main.indemnity.model.entity.Indemnity
import com.example.agriguard.modules.main.indemnity.model.mapper.toDTO
import com.example.agriguard.modules.main.indemnity.model.mapper.toEntity
import com.example.agriguard.modules.main.user.model.dto.UserDto
import com.example.agriguard.modules.main.user.service.UserService
import com.example.agriguard.modules.shared.ext.toInstantString
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.Sort
import io.realm.kotlin.types.RealmInstant
import org.mongodb.kbson.ObjectId
import javax.inject.Inject

class IndemnityInsuranceService @Inject constructor(
    private val realm: Realm,
    private val userService: UserService
) {
    suspend fun upsert(data: IndemnityDto, currentUser: UserDto): Result<IndemnityDto> {
        val dateNow = RealmInstant.now().toInstantString()
        if (data.id == null) {
            data.userId = currentUser.id!!
            data.createdById = currentUser.id
            data.createdAt = dateNow
            data.reviewById = currentUser.createdById
        }
        data.lastUpdatedById = currentUser.id
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

    fun fetchAll(userDto: UserDto): List<IndemnityWithUserDto> {
        val queryBuilder = StringBuilder()

        if (userDto.isFarmers) queryBuilder.append("userId == $0")
        if (userDto.isTechnician) queryBuilder.append("reviewById == $0")

        val userId = if (userDto.isAdmin) null
            else if (userDto.isTechnician) ObjectId(userDto.id!!)
            else userDto.id!!

        val query = if (userDto.isAdmin) realm.query<Indemnity>()
        else realm.query<Indemnity>(queryBuilder.toString(), userId)

        val userIndex = mutableMapOf<String, UserDto>()

        val entries = query
            .sort("fillUpDate", Sort.DESCENDING)
            .find()
            .map { it.toDTO() }

        return entries.map {
            val user = userIndex[it.userId] ?: userService.fetchOne(it.userId)
            userIndex[it.userId] = user

            IndemnityWithUserDto(it, user)
        }
    }

    fun fetchOne(id: String): IndemnityDto {
        Log.d("micool", "id: $id")
        return realm.query<Indemnity>("_id == $0", ObjectId(id))
            .find()
            .first()
            .run {
                toDTO()
            }
    }

    fun fetchFarmerWithIndemnity(farmerId: String, indemnityId: String): IndemnityWithUserDto? {
        try {
            val indemnity = realm.query<Indemnity>("_id == $0 AND userId == $1", ObjectId(indemnityId), farmerId)
                .find()
                .firstOrNull()
                ?.toDTO()
                ?: throw IllegalStateException("No indemnity found with ID: $indemnityId and Farmer ID: $farmerId")

            val user = userService.fetchOne(indemnity.userId)
            Log.d("QueryParameters", "indemnityId: $indemnityId, farmerId: $farmerId")
            return IndemnityWithUserDto(indemnity, user)
        } catch (e: Exception) {
            Log.e("fetchFarmerWithIndemnity", "Error: ${e.message}", e)
            return null
        }
    }
}