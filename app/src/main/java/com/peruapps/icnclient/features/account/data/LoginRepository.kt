package com.peruapps.icnclient.features.account.data

import com.peruapps.icnclient.model.response.FbValidationResponse
import com.peruapps.icnclient.model.response.LoginResponse
import com.peruapps.icnclient.model.response.MessageResponse

interface LoginRepository {

    suspend fun login(email: String, password: String): LoginResponse
    suspend fun emailValidation(email: String) : FbValidationResponse

}