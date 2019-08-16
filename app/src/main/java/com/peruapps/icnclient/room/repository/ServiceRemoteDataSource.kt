package com.peruapps.icnclient.room.repository

import com.peruapps.icnclient.model.Service

class ServiceRemoteDataSource: ServiceRepository.Remote {
    override fun getServices(): ArrayList<Service> {
        val arrayList = ArrayList<Service>()
        arrayList.add(Service(1, "Endovenoso", ""))
        arrayList.add(Service(2, "Intramuscular", ""))

        return arrayList
    }
//    override fun getServices(onServiceReadyCallback: OnServiceReadyCallback) {
//        val arrayList = ArrayList<Service>()
//        arrayList.add(Service(1, "Endovenoso", ""))
//        arrayList.add(Service(2, "Intramuscular", ""))
//        onServiceReadyCallback.onDataReady(arrayList)
//    }
}

interface OnServiceReadyCallback {
    fun onDataReady(data: ArrayList<Service>)
}