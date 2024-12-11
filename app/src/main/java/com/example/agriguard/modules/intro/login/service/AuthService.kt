package com.example.agriguard.modules.intro.login.service

import com.example.agriguard.modules.intro.login.model.dto.LoginDto
import com.example.agriguard.modules.main.notify.model.entity.Notify
import com.example.agriguard.modules.main.user.model.dto.UserDto
import com.example.agriguard.modules.main.user.model.entity.User
import com.example.agriguard.modules.main.user.model.mapper.toDTO
import com.example.agriguard.modules.shared.ext.verifyPassword
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.mongodb.subscriptions
import org.mongodb.kbson.ObjectId
import javax.inject.Inject

class AuthService @Inject constructor(
    private val realm: Realm,
) {
    suspend fun loadSpecificDataByUser(currentUserId: String) {
        realm.subscriptions.update {
            remove(Notify.COLLECTION_NAME)
            add(realm.query<Notify>("receiver == $0", ObjectId(currentUserId)), Notify.COLLECTION_NAME)
        }
    }

    fun login(loginDto: LoginDto): UserDto? {
        val query = "email == $0"

        val user = realm.query<User>(query, loginDto.email)
            .find()
            .firstOrNull()

        if (user == null || !user.password.verifyPassword(loginDto.password)) {
            return null
        }

        return user.toDTO()
    }
}

