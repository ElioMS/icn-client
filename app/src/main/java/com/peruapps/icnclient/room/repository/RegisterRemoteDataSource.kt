package com.peruapps.icnclient.room.repository

import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.ANRequest
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.peruapps.icnclient.model.request.RegisterRequest
import com.androidnetworking.interfaces.JSONObjectRequestListener
import org.json.JSONObject
import com.androidnetworking.common.ANResponse

class RegisterRemoteDataSource: RegisterRepository.Remote {

    data class User(val name: String)

    override fun createNewUser(data: RegisterRequest, onRegisterReadyCallback: OnRegisterReadyCallback) {

        AndroidNetworking.post("http://192.168.1.133:8000/api/v1/register")
            .addBodyParameter(data)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    onRegisterReadyCallback.onDataReady("Ok")
                }

                override fun onError(error: ANError) {
                    Log.e("ddd", error.errorBody.toString())
                }
            })
    }
}

interface OnRegisterReadyCallback{
    fun onDataReady(data: String)
}


