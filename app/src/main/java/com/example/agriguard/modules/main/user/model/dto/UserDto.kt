package com.example.agriguard.modules.main.user.model.dto

data class UserDto(
    var id: String? = null,
    var firstName: String = "",
    var middleName: String? = null,
    var lastName: String = "",
    var mobileNumber: String? = null,
    var address: String? = null,
    var username: String = "",
    var dateOfBirth: String = "",
    var password: String = "",
    var isAdmin: Boolean = false,
    var isTechnician: Boolean = false,
    var isFarmers: Boolean = false,
    var createdById: String? = null,
    var createdAt: String? = null,
    var lastUpdatedById: String? = null,
    var lastUpdatedAt: String? = null,
    var deletedById: String? = null,
    var deletedAt: String? = null,
)
