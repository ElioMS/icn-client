package com.peruapps.icnclient.features.account.data

import com.peruapps.icnclient.db.sharePreferences.PreferencesManager
import com.peruapps.icnclient.model.response.LoginResponse
import com.peruapps.icnclient.network.ApiService

class LoginRepositoryImpl(
    private val apiService: ApiService,
    private val preferencesManager: PreferencesManager
) : LoginRepository {

    override suspend fun login(email: String, password: String): LoginResponse {
        val response = apiService.login(email, password, 1)
        preferencesManager.saveAuthData(response)
        return response
    }
}