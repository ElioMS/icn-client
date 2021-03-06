package com.peruapps.icnclient.features.account.data

import com.peruapps.icnclient.db.sharePreferences.PreferencesManager
import com.peruapps.icnclient.model.response.FbValidationResponse
import com.peruapps.icnclient.model.response.LoginResponse
import com.peruapps.icnclient.model.response.MessageResponse
import com.peruapps.icnclient.network.ApiService

class LoginRepositoryImpl(
    private val apiService: ApiService,
    private val preferencesManager: PreferencesManager
) : LoginRepository {

    override suspend fun emailValidation(email: String): FbValidationResponse {
        val response = apiService.fbEmailValidation(email)

        if (response.data != null) {
            preferencesManager.saveAuthData(response.data)
        }

        return response
    }

    override suspend fun login(email: String, password: String): LoginResponse {
        val response = apiService.login(email, password, 1)
        preferencesManager.saveAuthData(response)
        return response
    }
}