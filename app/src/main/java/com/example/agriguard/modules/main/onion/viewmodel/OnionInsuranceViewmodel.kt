package com.example.agriguard.modules.main.onion.viewmodel

import androidx.lifecycle.ViewModel
import com.example.agriguard.modules.main.onion.model.dto.OnionInsuranceDto
import com.example.agriguard.modules.main.onion.service.OnionInsuranceService
import com.example.agriguard.modules.main.rice.model.dto.RiceInsuranceDto
import com.example.agriguard.modules.main.user.model.dto.UserDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class OnionInsuranceViewmodel  @Inject constructor(
    val service: OnionInsuranceService,
) : ViewModel() {
    private val _formState = MutableStateFlow(OnionInsuranceDto())
    val formState: StateFlow<OnionInsuranceDto> = _formState

    fun updateField(field: (OnionInsuranceDto) -> OnionInsuranceDto) {
        _formState.value = field(_formState.value)
    }

    suspend fun upsert(data: OnionInsuranceDto, currentUser: UserDto): Result<OnionInsuranceDto> {
        return service.upsert(data, currentUser)
    }

    fun fetchListOnionInsurance(userId: String): List<OnionInsuranceDto> {
        return service.fetchListInsurance(userId)
    }
}