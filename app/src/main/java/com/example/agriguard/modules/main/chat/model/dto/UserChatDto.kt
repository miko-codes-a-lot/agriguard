package com.example.agriguard.modules.main.chat.model.dto

import com.example.agriguard.modules.main.user.model.dto.UserDto
import kotlinx.datetime.Instant

data class UserChatDto(
    val userDto: UserDto,
    val chatDto: ChatDto,
    val updatedAt: Instant?
)
