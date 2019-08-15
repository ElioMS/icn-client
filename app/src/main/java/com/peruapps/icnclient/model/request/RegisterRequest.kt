package com.peruapps.icnclient.model.request

data class RegisterRequest(val name: String,
                           val surname: String,
                           val age: Int,
                           val gender: String,
                           val email: String,
                           val password: String,
                           val confirmPassword: String,
                           val is_admin: Int,
                           val type: Int)