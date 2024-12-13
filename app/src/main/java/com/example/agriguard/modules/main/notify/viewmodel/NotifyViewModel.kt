package com.example.agriguard.modules.main.notify.viewmodel

import androidx.lifecycle.ViewModel
import com.example.agriguard.modules.main.notify.model.dto.NotifyDto
import com.example.agriguard.modules.main.notify.service.NotifyService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotifyViewModel @Inject constructor(
    private val notifyService: NotifyService
): ViewModel() {

    fun fetchAllNotify(): List<NotifyDto> {
        return notifyService.fetchAll()
    }

    suspend fun markAsRead(notifyDto: NotifyDto): Result<NotifyDto> {
        return notifyService.markAsRead(notifyDto)
    }
}