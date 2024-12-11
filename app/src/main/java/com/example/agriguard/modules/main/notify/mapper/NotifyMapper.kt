package com.example.agriguard.modules.main.notify.mapper

import com.example.agriguard.modules.main.notify.model.dto.NotifyDto
import com.example.agriguard.modules.main.notify.model.entity.Notify
import com.example.agriguard.modules.shared.ext.toInstantString
import com.example.agriguard.modules.shared.ext.toObjectId
import com.example.agriguard.modules.shared.ext.toRealmInstant

fun Notify.toDTO(): NotifyDto {
    return NotifyDto(
        id = _id.toHexString(),
        sender = sender.toHexString(),
        receiver = receiver.toHexString(),
        documentId = documentId?.toHexString(),
        documentType = documentType,
        message = message,
        read = read,
        createdAt = createdAt.toInstantString(),
    )
}

fun NotifyDto.toEntity(): Notify {
    val notifyDto = this
    return Notify().apply {
        _id = notifyDto.id.toObjectId()
        sender = notifyDto.sender.toObjectId()
        receiver = notifyDto.receiver.toObjectId()
        documentId = notifyDto.documentId?.toObjectId()
        documentType = notifyDto.documentType
        message = notifyDto.message
        read = notifyDto.read
        createdAt = notifyDto.createdAt.toRealmInstant()
    }
}