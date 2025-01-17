package com.example.agriguard.modules.main.user.viewmodel

import androidx.lifecycle.ViewModel
import com.example.agriguard.modules.main.complain.model.dto.ComplaintInsuranceDto
import com.example.agriguard.modules.main.user.model.dto.UserDto
import com.example.agriguard.modules.main.user.service.UserService
import dagger.hilt.android.lifecycle.HiltViewModel
import org.mongodb.kbson.ObjectId
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

    fun getComplaintsByAddress(address: String?, month: Int): List<ComplaintInsuranceDto> {
        return userService.fetchComplaintsByUsersAddress(address, month)
    }

    fun getComplaintsByDate(userId: ObjectId, isAdmin: Boolean = false, month: Int? = null): List<ComplaintInsuranceDto> {
        val id = if (!isAdmin) userId else null
        return userService.fetchComplaintsByDate(userId = id, month = month)
    }

    fun fetchUserByEmail(email: String): UserDto? {
        return userService.fetchByEmail(email)
    }

    fun fetchUserByEmailAndToken(email: String, token: String): UserDto? {
        return userService.fetchEmailAndToken(email, token)
    }

}
