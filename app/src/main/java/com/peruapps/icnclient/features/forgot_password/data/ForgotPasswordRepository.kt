package com.peruapps.icnclient.features.forgot_password.data

import com.peruapps.icnclient.model.response.MessageResponse
import com.peruapps.icnclient.model.response.PasswordResetTokenResponse

interface ForgotPasswordRepository {
    suspend fun tokenRequest(email: String): MessageResponse
    suspend fun tokenValidation(token: String): PasswordResetTokenResponse
}