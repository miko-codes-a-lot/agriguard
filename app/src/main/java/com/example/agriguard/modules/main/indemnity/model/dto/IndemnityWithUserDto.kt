package com.example.agriguard.modules.main.indemnity.model.dto

import com.example.agriguard.modules.main.user.model.dto.UserDto

data class IndemnityWithUserDto(val indemnity: IndemnityDto, val user: UserDto)
