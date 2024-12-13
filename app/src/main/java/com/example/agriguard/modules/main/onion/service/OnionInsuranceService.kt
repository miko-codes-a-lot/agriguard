package com.example.agriguard.modules.main.onion.service

import com.example.agriguard.modules.main.onion.mapper.toDTO
import com.example.agriguard.modules.main.onion.mapper.toEntity
import com.example.agriguard.modules.main.onion.model.dto.OnionInsuranceDto
import com.example.agriguard.modules.main.onion.model.dto.OnionWithUserDto
import com.example.agriguard.modules.main.onion.model.entity.OnionInsurance
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

class OnionInsuranceService @Inject constructor(
    private val userService: UserService,
    private val realm: Realm,
)  {
    suspend fun upsert(data: OnionInsuranceDto, currentUser: UserDto): Result<OnionInsuranceDto> {
        val dateNow = RealmInstant.now().toInstantString()
        if (data.id == null) {
            data.userId = currentUser.id!!
            data.createdById = currentUser.id!!
            data.createdAt = dateNow
            data.reviewById = currentUser.createdById
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

    fun fetchAll(userDto: UserDto): List<OnionWithUserDto> {
        val queryBuilder = StringBuilder()

        if (userDto.isFarmers) queryBuilder.append("userId == $0")
        if (userDto.isTechnician) queryBuilder.append("reviewById == $0")

        val userId = if (userDto.isAdmin) null
        else if (userDto.isTechnician) ObjectId(userDto.id!!)
        else userDto.id!!

        val query = if (userDto.isAdmin) realm.query<OnionInsurance>()
        else realm.query<OnionInsurance>(queryBuilder.toString(), userId)

        val userIndex = mutableMapOf<String, UserDto>()

        val entries = query
            .sort("fillUpDate", Sort.DESCENDING)
            .find()
            .map { it.toDTO() }

        return entries.map {
            val user = userIndex[it.userId] ?: userService.fetchOne(it.userId)
            userIndex[it.userId] = user

            OnionWithUserDto(it, user)
        }
    }

    fun fetchOne(id: String): OnionInsuranceDto {
        return realm.query<OnionInsurance>("_id == $0", ObjectId(id))
            .find()
            .first()
            .run {
                toDTO()
            }
    }
}