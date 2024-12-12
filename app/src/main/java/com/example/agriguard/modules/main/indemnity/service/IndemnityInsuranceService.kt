package com.example.agriguard.modules.main.indemnity.service

import com.example.agriguard.modules.main.indemnity.model.dto.IndemnityDto
import com.example.agriguard.modules.main.indemnity.model.entity.Indemnity
import com.example.agriguard.modules.main.indemnity.model.mapper.toDTO
import com.example.agriguard.modules.main.indemnity.model.mapper.toEntity
import com.example.agriguard.modules.main.user.model.dto.UserDto
import com.example.agriguard.modules.shared.ext.toInstantString
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.Sort
import io.realm.kotlin.types.RealmInstant
import org.mongodb.kbson.ObjectId
import javax.inject.Inject

class IndemnityInsuranceService @Inject constructor(private val realm: Realm) {
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

    fun fetchAll(userDto: UserDto): List<IndemnityDto> {
        val queryBuilder = StringBuilder()

        if (userDto.isFarmers) queryBuilder.append("userId == $0")
        if (userDto.isTechnician) queryBuilder.append("reviewById == $0")

        val userId = if (userDto.isAdmin) null
            else if (userDto.isTechnician) ObjectId(userDto.id!!)
            else userDto.id!!

        return realm.query<Indemnity>(queryBuilder.toString(), userId)
            .sort("fillupdate", Sort.DESCENDING)
            .find()
            .map { it.toDTO() }
    }

    fun fetchOne(id: String): IndemnityDto {
        return realm.query<Indemnity>("_id == $0", ObjectId(id))
            .find()
            .first()
            .run {
                toDTO()
            }
    }
}