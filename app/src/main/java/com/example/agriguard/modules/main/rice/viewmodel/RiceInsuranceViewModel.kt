package com.example.agriguard.modules.main.rice.viewmodel

import androidx.lifecycle.ViewModel
import com.example.agriguard.modules.main.rice.service.RiceInsuranceService
import com.example.agriguard.modules.main.rice.model.dto.RiceInsuranceDto
import com.example.agriguard.modules.main.rice.model.dto.RiceWIthUserDto
import com.example.agriguard.modules.main.user.model.dto.UserDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class RiceInsuranceViewModel @Inject constructor(
    val service: RiceInsuranceService,
) : ViewModel() {
    private val _formState = MutableStateFlow(RiceInsuranceDto())
    val formState: StateFlow<RiceInsuranceDto> = _formState

    fun setFormState(riceInsuranceDto: RiceInsuranceDto) {
        _formState.value = riceInsuranceDto
    }

    fun updateField(field: (RiceInsuranceDto) -> RiceInsuranceDto) {
        _formState.value = field(_formState.value)
    }

    fun fetchOne(id: String): RiceInsuranceDto {
        return service.fetchOne(id)
    }

    suspend fun upsert(data: RiceInsuranceDto, currentUser: UserDto): Result<RiceInsuranceDto> {
        return service.upsert(data, currentUser)
    }

    fun fetchAll(userDto: UserDto): List<RiceWIthUserDto> {
        return service.fetchAll(userDto)
    }
}