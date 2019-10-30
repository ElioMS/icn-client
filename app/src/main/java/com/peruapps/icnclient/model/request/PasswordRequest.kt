package com.peruapps.icnclient.model.request

import com.google.gson.annotations.SerializedName

data class PasswordRequest(
    @SerializedName("current_password") var password: String,
    @SerializedName("new_password") var newPassword: String)