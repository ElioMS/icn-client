package com.peruapps.icnclient.room.repository

import android.util.Log
import com.peruapps.icnclient.model.request.RegisterRequest
import org.json.JSONObject


class RegisterRemoteDataSource: RegisterRepository.Remote {

    data class User(val name: String)

    override fun createNewUser(data: RegisterRequest, onRegisterReadyCallback: OnRegisterReadyCallback) {


    }
}

interface OnRegisterReadyCallback{
    fun onDataReady(data: String)
}


