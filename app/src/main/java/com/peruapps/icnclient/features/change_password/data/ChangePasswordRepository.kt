package com.peruapps.icnclient.features.change_password.data

import com.peruapps.icnclient.model.request.PasswordRequest
import okhttp3.ResponseBody

interface ChangePasswordRepository {
    suspend fun changePassword(data: PasswordRequest): ResponseBody
}