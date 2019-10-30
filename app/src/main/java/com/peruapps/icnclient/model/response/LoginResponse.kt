package com.peruapps.icnclient.model.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    val name: String,
    val surname: String,
    var email: String,
    var gender: String,
    @SerializedName("document_type") var documentType: Int,
    var document_number: String,
    var phone_number: String,
    var address: String,
    @SerializedName("address_reference") var addressReference: String,
    var age: Int,
    var photo: String,
    var token: String
) {

    fun fullName(): String {
        return "${this.name} ${this.surname}"
    }

    fun genderToString(): String {
        return when (this.gender) {
            "M" -> "Masculino"
            else -> "Femenino"
        }
    }

    fun documentTypeToString(): String {
        return when(this.documentType) {
            1 -> "DNI"
            else -> "PASAPORTE"
        }
    }
}