package com.example.agriguard.modules.main.user.viewmodel

import androidx.lifecycle.ViewModel
import com.example.agriguard.modules.main.user.model.dto.UserDto
import com.example.agriguard.modules.main.user.service.UserService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    val userService: UserService
): ViewModel() {

    fun fetchUsers(): List<UserDto> {
        return this.userService.fetch()
    }

    suspend fun upsertUser(userDto: UserDto, actionOf: UserDto): Result<UserDto> {
        return this.userService.upsert(userDto, actionOf)
    }

    fun fetchUser(userId: String): UserDto {
        return this.userService.fetchOne(userId)
    }

}
