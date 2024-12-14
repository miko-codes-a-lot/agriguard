package com.example.agriguard.modules.main.complain.model.dto

import com.example.agriguard.modules.main.user.model.dto.UserDto

data class ComplainWithUserDto(val complaint: ComplaintInsuranceDto, val user: UserDto)