package com.example.agriguard.modules.intro.login.service

import com.example.agriguard.modules.intro.login.model.dto.LoginDto
import com.example.agriguard.modules.main.user.model.dto.UserDto
import com.example.agriguard.modules.main.user.model.entity.User
import com.example.agriguard.modules.main.user.model.mapper.toDTO
import com.example.agriguard.modules.shared.ext.verifyPassword
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import javax.inject.Inject

class AuthService @Inject constructor(
    private val realm: Realm,
) {

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

