package com.peruapps.icnclient.features.change_password.data

import com.peruapps.icnclient.model.request.PasswordRequest
import com.peruapps.icnclient.network.ApiService
import okhttp3.ResponseBody

class ChangePasswordRepositoryImpl(private val apiService: ApiService) : ChangePasswordRepository {

    override suspend fun changePassword(data: PasswordRequest): ResponseBody {
        return apiService.changePassword(data)
    }

}