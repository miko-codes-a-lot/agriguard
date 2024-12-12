package com.example.agriguard.modules.main.indemnity.viewmodel

import androidx.lifecycle.ViewModel
import com.example.agriguard.modules.main.indemnity.service.IndemnityInsuranceService
import com.example.agriguard.modules.main.indemnity.model.dto.IndemnityDto
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

    fun updateField(field: (IndemnityDto) -> IndemnityDto) {
        _formState.value = field(_formState.value)
    }

    suspend fun upsert(data: IndemnityDto, currentUser: UserDto): Result<IndemnityDto> {
        return service.upsert(data, currentUser)
    }

    fun fetchListIndemnity(userId: String): List<IndemnityDto> {
        return service.fetchListIndemnity(userId)
    }
}
