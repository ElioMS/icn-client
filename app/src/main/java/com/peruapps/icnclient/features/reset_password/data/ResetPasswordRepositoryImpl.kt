package com.peruapps.icnclient.features.reset_password.data

import com.peruapps.icnclient.db.sharePreferences.PreferencesManager
import com.peruapps.icnclient.model.request.ResetPasswordRequest
import com.peruapps.icnclient.model.response.LoginResponse
import com.peruapps.icnclient.network.ApiService

class ResetPasswordRepositoryImpl (private val apiService: ApiService,
                                   private val preferencesManager: PreferencesManager
): ResetPasswordRepository {
    override suspend fun resetPassword(data: ResetPasswordRequest): LoginResponse {
        val response = apiService.resetPassword(data)
        preferencesManager.saveAuthData(response)
        return response
    }
}