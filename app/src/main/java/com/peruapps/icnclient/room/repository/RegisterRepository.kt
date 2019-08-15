package com.peruapps.icnclient.room.repository

import android.content.Context
import com.peruapps.icnclient.model.request.RegisterRequest

interface RegisterRepository {

    interface Remote {
        fun createNewUser(data: RegisterRequest)
    }
}