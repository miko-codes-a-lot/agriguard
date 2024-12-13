package com.example.agriguard.modules.main.rice.service

import com.example.agriguard.modules.main.rice.model.dto.RiceInsuranceDto
import com.example.agriguard.modules.main.user.model.dto.UserDto
import com.example.agriguard.modules.main.rice.mapper.toDTO
import com.example.agriguard.modules.main.rice.mapper.toEntity
import com.example.agriguard.modules.main.rice.model.dto.RiceWIthUserDto
import com.example.agriguard.modules.main.rice.model.entity.RiceInsurance
import com.example.agriguard.modules.main.user.service.UserService
import com.example.agriguard.modules.shared.ext.toInstantString
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.Sort
import io.realm.kotlin.types.RealmInstant
import org.mongodb.kbson.ObjectId
import javax.inject.Inject

class RiceInsuranceService @Inject constructor(
    private val userService: UserService,
    private val realm: Realm
)  {
    suspend fun upsert(data: RiceInsuranceDto, currentUser: UserDto): Result<RiceInsuranceDto> {
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

    fun fetchAll(userDto: UserDto): List<RiceWIthUserDto> {
        val queryBuilder = StringBuilder()

        if (userDto.isFarmers) queryBuilder.append("userId == $0")
        if (userDto.isTechnician) queryBuilder.append("reviewById == $0")

        val userId = if (userDto.isAdmin) null
        else if (userDto.isTechnician) ObjectId(userDto.id!!)
        else userDto.id!!

        val query = if (userDto.isAdmin) realm.query<RiceInsurance>()
        else realm.query<RiceInsurance>(queryBuilder.toString(), userId)

        val userIndex = mutableMapOf<String, UserDto>()

        val entries = query
            .sort("fillUpDate", Sort.DESCENDING)
            .find()
            .map { it.toDTO() }

        return entries.map {
            val user = userIndex[it.userId] ?: userService.fetchOne(it.userId)
            userIndex[it.userId] = user

            RiceWIthUserDto(it, user)
        }
    }

    fun fetchOne(id: String): RiceInsuranceDto {
        return realm.query<RiceInsurance>("_id == $0", ObjectId(id))
            .find()
            .first()
            .run {
                toDTO()
            }
    }
}