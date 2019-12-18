package com.peruapps.icnclient.features.forgot_password.data

import com.peruapps.icnclient.model.response.MessageResponse
import com.peruapps.icnclient.model.response.PasswordResetTokenResponse
import com.peruapps.icnclient.network.ApiService

class ForgotPasswordRepositoryImpl (private val apiService: ApiService): ForgotPasswordRepository {

    override suspend fun tokenValidation(token: String): PasswordResetTokenResponse {
        return apiService.passwordRequestTokenValidation(token)
    }

    override suspend fun tokenRequest(email: String): MessageResponse {
        return apiService.passwordResetRequest(email)
    }

}