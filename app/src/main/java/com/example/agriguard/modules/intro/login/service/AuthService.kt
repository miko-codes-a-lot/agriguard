package com.example.agriguard.modules.intro.login.service

import com.example.agriguard.modules.intro.login.model.dto.LoginDto
import com.example.agriguard.modules.main.notify.model.entity.Notify
import com.example.agriguard.modules.main.user.model.dto.UserDto
import com.example.agriguard.modules.main.user.model.entity.User
import com.example.agriguard.modules.main.user.model.mapper.toDTO
import com.example.agriguard.modules.shared.ext.plusSeconds
import com.example.agriguard.modules.shared.ext.verifyPassword
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.mongodb.subscriptions
import io.realm.kotlin.types.RealmInstant
import org.mongodb.kbson.ObjectId
import javax.inject.Inject

class AuthService @Inject constructor(
    private val realm: Realm
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

    fun requestOTP(email: String): Boolean {
        val user = realm.query<User>("email == $0", email).find().firstOrNull()
            ?: throw IllegalArgumentException("Invalid email. User not found.")

        val existingToken = user.resetPasswordToken
        val tokenExpiration = user.resetTokenExpiration
        val now = RealmInstant.now()

        if (existingToken != null && tokenExpiration != null && now < tokenExpiration) {
            throw IllegalArgumentException("An OTP has already been generated. Please wait until it expires.")
        }

        val otp = (100000..999999).random().toString()
        val expirationTime = now.plusSeconds(5 * 60)

        realm.writeBlocking {
            findLatest(user)?.apply {
                this.resetPasswordToken = otp
                this.resetTokenExpiration = expirationTime
            }
        }

        return true
    }

    fun verifyResetToken(email: String, token: String): Boolean {
        if (email.isBlank() || token.isBlank()) {
            throw IllegalArgumentException("Email and token cannot be blank.")
        }

        val user = realm.query<User>("email == $0", email).find().firstOrNull()
            ?: throw IllegalArgumentException("Invalid email. User not found.")

        val storedToken = user.resetPasswordToken
            ?: throw IllegalArgumentException("No reset token found for this user.")
//        val tokenExpiration = user.tokenExpiration
//        if (storedToken == null || tokenExpiration == null) {
        if (storedToken != token) {
            throw IllegalArgumentException("Invalid token. Please check your token and try again.")
        }
//        if (tokenExpiration.isBefore(java.time.Instant.now())) {
//            throw IllegalArgumentException("Token has expired. Please request a new one.")
//        }
        return true
    }
}

