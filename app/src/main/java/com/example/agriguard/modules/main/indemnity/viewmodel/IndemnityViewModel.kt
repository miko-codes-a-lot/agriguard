package com.example.agriguard.modules.main.indemnity.viewmodel

import androidx.lifecycle.ViewModel
import com.example.agriguard.modules.main.indemnity.model.dto.IndemnityDto
import com.example.agriguard.modules.main.indemnity.model.dto.IndemnityWithUserDto
import com.example.agriguard.modules.main.indemnity.service.IndemnityInsuranceService
import com.example.agriguard.modules.main.user.model.dto.UserDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class IndemnityViewModel @Inject constructor(
    val service: IndemnityInsuranceService,
) : ViewModel() {
    private val _formState = MutableStateFlow(IndemnityDto())
    val formState: StateFlow<IndemnityDto> = _formState

    fun setFormState(indemnityDto: IndemnityDto) {
        _formState.value = indemnityDto
    }

    fun updateField(field: (IndemnityDto) -> IndemnityDto) {
        _formState.value = field(_formState.value)
    }

    fun fetchOne(id: String): IndemnityDto {
        return service.fetchOne(id)
    }

    suspend fun upsert(data: IndemnityDto, currentUser: UserDto): Result<IndemnityDto> {
        return service.upsert(data, currentUser)
    }

    fun fetchAll(userDto: UserDto): List<IndemnityWithUserDto> {
        return service.fetchAll(userDto = userDto)
    }
}
