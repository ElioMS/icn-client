package com.peruapps.icnclient.room.repository

import com.peruapps.icnclient.model.request.RegisterRequest

interface RegisterRepository {

    interface Remote {
        fun createNewUser(data: RegisterRequest, onRegisterReadyCallback: OnRegisterReadyCallback)
    }
}