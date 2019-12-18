package com.peruapps.icnclient.features.reset_password.data

import com.peruapps.icnclient.model.request.ResetPasswordRequest
import com.peruapps.icnclient.model.response.LoginResponse

interface ResetPasswordRepository {
    suspend fun resetPassword(data: ResetPasswordRequest): LoginResponse
}