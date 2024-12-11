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
    data class CreateUser(val addressId: String?)

    @Serializable
    object FarmersPreview

    @Serializable
    object Dashboard

    @Serializable
    data class ComplainForm(val userId: String)

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
    data class RiceInsuranceForm(val userId: String)

    @Serializable
    data class RiceInsuranceList(val userId: String)

    @Serializable
    data class InDemnityForm(val userId: String)

    @Serializable
    data class InDemnityList(val userId: String)

    @Serializable
    data class OnionInsuranceForm(val userId: String)

    @Serializable
    data class OnionInsuranceList(val userId: String)

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
    object ComplaintReportList

    @Serializable
    object Registration

}