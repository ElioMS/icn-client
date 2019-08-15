package com.peruapps.icnclient.room.repository

import android.content.Context
import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.peruapps.icnclient.model.request.RegisterRequest
import org.json.JSONArray
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.google.gson.Gson
import org.json.JSONObject
import com.jacksonandroidnetworking.JacksonParserFactory



class RegisterRemoteDataSource: RegisterRepository.Remote {

    override fun createNewUser(data: RegisterRequest) {

        AndroidNetworking.post("http://192.168.1.133:8000/api/v1/register")
            .addBodyParameter(data)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    Log.d("register", response.toString())
                }

                override fun onError(error: ANError) {
                    Log.e("ddd", error.errorBody.toString())
                }
            })
//        Log.d("register_viewmodel", data.toString())
    }

}