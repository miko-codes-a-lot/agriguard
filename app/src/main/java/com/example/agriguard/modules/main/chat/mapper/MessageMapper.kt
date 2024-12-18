package com.example.agriguard.modules.main.chat.mapper

import com.example.agriguard.modules.main.chat.model.dto.MessageDto
import com.example.agriguard.modules.main.chat.model.entity.Message
import com.example.agriguard.modules.shared.ext.toInstantK
import com.example.agriguard.modules.shared.ext.toObjectId
import com.example.agriguard.modules.shared.ext.toRealmInstant


fun Message.toDto(): MessageDto {
    return MessageDto(
        id = _id.toHexString(),
        chatId = chatId!!.toHexString(),
        senderId = senderId!!.toHexString(),
        receiverId = receiverId!!.toHexString(),
        content = content,
        createdAt = createdAt.toInstantK(),
    )
}

fun MessageDto.toEntity(): Message {
    val messageDto = this
    return Message().apply {
        _id = messageDto.id.toObjectId()
        chatId = messageDto.chatId.toObjectId()
        senderId = messageDto.senderId.toObjectId()
        receiverId = messageDto.receiverId.toObjectId()
        content = messageDto.content
        createdAt = messageDto.createdAt.toRealmInstant()
    }
}