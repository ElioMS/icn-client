package com.peruapps.icnclient.room.repository

import com.peruapps.icnclient.model.Service

interface ServiceRepository {

    interface Remote {
//        fun getServices(onServiceReadyCallback: OnServiceReadyCallback)
        fun getServices(): ArrayList<Service>
    }

    interface Local {
        fun saveServices() {

        }
    }
}