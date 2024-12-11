package com.example.agriguard.modules.main.notify.service

import com.example.agriguard.modules.main.notify.mapper.toDTO
import com.example.agriguard.modules.main.notify.model.dto.NotifyDto
import com.example.agriguard.modules.main.notify.model.entity.Notify
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import javax.inject.Inject

class NotifyService @Inject constructor(private val realm: Realm)  {
    fun fetchAll(): List<NotifyDto> {
        return realm.query<Notify>()
            .find()
            .map { it.toDTO() }
    }
}