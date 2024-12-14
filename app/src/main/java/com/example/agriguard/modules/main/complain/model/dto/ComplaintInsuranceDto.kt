package com.example.agriguard.modules.main.complain.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class ComplaintInsuranceDto (
    var id: String? = null,
    var userId: String = "",
    var rice: Boolean = false,
    var onion: Boolean = false,
    var causeOfDamage: String? = null,
    var imageBase64: String? = null,
    var createdById: String? = null,
    var status: String? = "pending",
    var createdAt: String? = null,
    var reviewById: String? = null,
    var lastUpdatedById: String? = null,
    var lastUpdatedAt: String? = null,
    var deletedById: String? = null,
    var deletedAt: String? = null,
)