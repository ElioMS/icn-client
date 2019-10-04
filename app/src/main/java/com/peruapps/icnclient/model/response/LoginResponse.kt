package com.peruapps.icnclient.model.response

data class LoginResponse(val name: String,
                        val surname: String,
                        val email: String,
                        val gender: String,
                        val document_number: String,
                        val phone_number: String,
                        val age: Int,
                        val photo: String,
                        val token: String) {

    fun fullName(): String {
        return "${this.name} ${this.surname}"
    }

    fun genderToString(): String {
        return when(this.gender) {
            "M" -> "Masculino"
            else -> "Femenino"
        }
    }
}