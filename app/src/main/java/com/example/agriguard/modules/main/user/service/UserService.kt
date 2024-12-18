package com.example.agriguard.modules.main.user.service

import com.example.agriguard.modules.main.user.model.dto.UserDto
import com.example.agriguard.modules.main.user.model.entity.User
import com.example.agriguard.modules.main.user.model.mapper.toDTO
import com.example.agriguard.modules.main.user.model.mapper.toEntity
import com.example.agriguard.modules.shared.ext.hashPassword
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

    fun fetch(
        isFarmers: Boolean = false,
        userId: ObjectId? = null,
        addressName: String? = null,
    ): List<UserDto> {
        val query = StringBuilder()
            .append("isFarmers == $0")
        if(userId != null) query.append(" AND createdById == $1")
        if (addressName != null) query.append(" AND address = $2")

        return realm.query<User>(
            query.toString(), isFarmers, userId, addressName
        )
            .find()
            .map { user -> user.toDTO() }
    }

    suspend fun saveValidId(userId: String, imageUri: ByteArray?): Result<UserDto> {
        return try {
            realm.write {
                val user = query<User>("_id == $0", ObjectId(userId)).find().firstOrNull()
                if (user != null && imageUri != null) {
                    val validIdImage =
                        android.util.Base64.encodeToString(imageUri, android.util.Base64.DEFAULT)
                    user.validId = validIdImage
                    copyToRealm(user, UpdatePolicy.ALL)
                    Result.success(user.toDTO())
                } else {
                    Result.failure(Exception())
                }
            }
        } catch (error: Exception) {
            Result.failure(error)
        }
    }

    suspend fun saveImage(userId: String, imageUri: ByteArray?): Result<UserDto> {
        return try {
            realm.write {
                val user = query<User>("_id == $0", ObjectId(userId)).find().firstOrNull()
                if (user != null && imageUri != null) {
                    val validIdImage =
                        android.util.Base64.encodeToString(imageUri, android.util.Base64.DEFAULT)
                    user.userProfile = validIdImage
                    copyToRealm(user, UpdatePolicy.ALL)
                    Result.success(user.toDTO())
                } else {
                    Result.failure(Exception())
                }
            }
        } catch (error: Exception) {
            Result.failure(error)
        }
    }

    suspend fun saveNewPassword(email: String, token: String, newPassword: String): Boolean {
        val hashedPassword = newPassword.hashPassword()
        return realm.write {
            val user = fetchEmailAndToken(email, token)
            if (user != null) {
                user.password = hashedPassword
                copyToRealm(user.toEntity(), updatePolicy = UpdatePolicy.ALL)
                true
            } else {
                false
            }
        }
    }

    private fun fetchEmailAndToken(email: String, token: String): UserDto? {
        val user = realm.query<User>("email == $0 AND resetPasswordToken == $1", email, token).find().firstOrNull()
        if (user != null) {
            return user.toDTO()
        }
        return null
    }
}