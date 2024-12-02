package com.example.agriguard.modules.main.user.service

import com.example.agriguard.modules.main.user.model.dto.UserDto
import com.example.agriguard.modules.main.user.model.entity.User
import com.example.agriguard.modules.main.user.model.mapper.toDTO
import com.example.agriguard.modules.main.user.model.mapper.toEntity
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import org.mongodb.kbson.ObjectId
import javax.inject.Inject

class UserService  @Inject constructor(private val realm: Realm)  {
    fun fetchOne(userId: String): UserDto {
        return realm.query<User>("_id == $0", ObjectId(userId))
            .find()
            .first()
            .run {
                toDTO()
            }
    }

    suspend fun upsert(data: UserDto, actionOf: UserDto): Result<UserDto> {
        return try {
            realm.write {
                data.createdById = actionOf.id
                data.lastUpdatedById = data.lastUpdatedById ?: actionOf.id
                val user = copyToRealm(data.toEntity(), updatePolicy = UpdatePolicy.ALL)
                Result.success(user.toDTO())
            }
        } catch (error: Exception) {
            Result.failure(error)
        }
    }
}