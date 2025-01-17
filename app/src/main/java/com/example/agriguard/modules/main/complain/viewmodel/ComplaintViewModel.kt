package com.example.agriguard.modules.main.complain.viewmodel

import androidx.lifecycle.ViewModel
import com.example.agriguard.modules.main.complain.model.dto.ComplainWithUserDto
import com.example.agriguard.modules.main.complain.model.dto.ComplaintInsuranceDto
import com.example.agriguard.modules.main.complain.service.ComplaintService
import com.example.agriguard.modules.main.user.model.dto.UserDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ComplaintViewModel @Inject constructor (
    val service: ComplaintService,
) : ViewModel() {
    private val _formState = MutableStateFlow(ComplaintInsuranceDto())
    val formState: StateFlow<ComplaintInsuranceDto> = _formState

    fun setFormState(complaintDto: ComplaintInsuranceDto) {
        _formState.value = complaintDto
    }

    fun updateField(field: (ComplaintInsuranceDto) -> ComplaintInsuranceDto) {
        _formState.value = field(_formState.value)
    }

    fun fetchOne(id: String): ComplaintInsuranceDto {
        return service.fetchOne(id)
    }

    suspend fun upsert(complaintDto: ComplaintInsuranceDto, currentUser: UserDto): Result<ComplaintInsuranceDto> {
        return service.upsertComplaint(complaintDto, currentUser)
    }

    fun fetchList(userDto: UserDto): List<ComplainWithUserDto> {
        return service.fetchAll(userDto = userDto)
    }

}