package com.peruapps.icnclient.model.request

import com.google.gson.annotations.SerializedName

data class UpdateProfileRequest (
//    val name: String,
//    val surname: String,
    val email: String,
    val gender: String,
    @SerializedName("document_type") var documentType: Int,
    var document_number: String,
    val phone_number: String,
    val address: String,
    @SerializedName("address_reference") val addressReference: String,
    val age: Int,
    val photo: String? = null
)