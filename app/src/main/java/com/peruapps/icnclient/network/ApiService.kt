package com.peruapps.icnclient.network

import com.peruapps.icnclient.model.response.LoginResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @POST("login")
    @FormUrlEncoded
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("type") type: Int
    ): LoginResponse
}