package com.peruapps.icnclient.network

import android.content.SharedPreferences
import com.google.gson.GsonBuilder
import com.peruapps.icnclient.db.sharePreferences.PreferencesManager
import com.peruapps.icnclient.model.response.LoginResponse
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class BasicAuthInterceptor(private val preferencesManager: PreferencesManager) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
//
//        val authInfo = preferences.getString("auth", "")
//        var token = ""
//
//        if (authInfo != "") {
//            val gson = GsonBuilder().create()
//            val response = gson.fromJson(authInfo, LoginResponse::class.java)
//            token = response.token
//        }

        val request = chain.request()
        val authenticatedRequest = request.newBuilder()
            .header("Authorization", "Bearer ${preferencesManager.token}").build()
        return chain.proceed(authenticatedRequest)
    }
}