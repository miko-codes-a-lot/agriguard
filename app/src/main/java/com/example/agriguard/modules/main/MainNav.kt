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
    object ComplainForm

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
    object RiceInsuranceForm

    @Serializable
    object RiceInsuranceList

    @Serializable
    object InDemnityForm

    @Serializable
    object InDemnityList

    @Serializable
    object OnionInsuranceForm

    @Serializable
    object OnionInsuranceList

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