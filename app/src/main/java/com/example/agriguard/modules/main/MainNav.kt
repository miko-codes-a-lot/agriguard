package com.example.agriguard.modules.main

import kotlinx.serialization.Serializable

@Serializable
object MainNav {
    @Serializable
    object Menu

    @Serializable
    data class Farmers(
        val status: String,
        var addressId: String? = null
    )

    @Serializable
    object Users

    @Serializable
    data class EditUser(val userId: String)

    @Serializable
    object Addresses

    @Serializable
    object ChatLobby

    @Serializable
    data class ChatDirect(val userId: String)

    @Serializable
    data class CreateUser(val addressId: String?)

    @Serializable
    data class FarmersPreview(val userId: String)

    @Serializable
    object Dashboard

    @Serializable
    object Message

    @Serializable
    object MessageList

    @Serializable
    object NotificationList

    @Serializable
    object FormValidation

    @Serializable
    object Setting

    @Serializable
    object RiceCreate

    @Serializable
    object RiceInsuranceList

    @Serializable
    data class RiceInsuranceDetails(val id: String, val userId: String)

    @Serializable
    data class RiceInsuranceEdit(val id: String)

    @Serializable
    data class IndemnityDetails(val id: String, val userId: String)

    @Serializable
    object IndemnityCreate

    @Serializable
    data class IndemnityEdit(val id: String)

    @Serializable
    object IndemnityList

    @Serializable
    object OnionCreate

    @Serializable
    object OnionInsuranceList

    @Serializable
    object ComplainCreate

    @Serializable
    data class ComplainEdit(val id: String)

    @Serializable
    object ComplaintList

    @Serializable
    data class ComplaintDetails(val id: String, val userId: String)

    @Serializable
    data class OnionEdit(val id: String)

    @Serializable
    data class OnionDetails(val id: String, val userId: String)

    @Serializable
    object RiceDisease

    @Serializable
    object OnionDisease

    @Serializable
    object RicePets

    @Serializable
    object OnionPets

    @Serializable
    object OnionWeed

    @Serializable
    object RiceWeed

    @Serializable
    object Registration

    @Serializable
    object ReportDashboard

    @Serializable
    data class UserValidId (val userId: String)

    @Serializable
    object EditSettings
}