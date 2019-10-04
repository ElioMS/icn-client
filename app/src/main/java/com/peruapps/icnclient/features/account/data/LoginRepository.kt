package com.peruapps.icnclient.features.account.data

import com.peruapps.icnclient.model.response.LoginResponse

interface LoginRepository {

    suspend fun login(email: String, password: String): LoginResponse

}