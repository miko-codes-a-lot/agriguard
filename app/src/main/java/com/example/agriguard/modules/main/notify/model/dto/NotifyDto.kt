package com.example.agriguard.modules.main.notify.model.dto

import com.example.agriguard.modules.shared.ext.toInstantString
import io.realm.kotlin.types.RealmInstant

data class NotifyDto(
    var id: String? = null,
    val sender: String,
    val receiver: String,
    val documentId: String? = null,
    val documentType: String? = null,
    val message: String = "",
    val read: Boolean = false,
    val createdAt: String = RealmInstant.now().toInstantString(),
)