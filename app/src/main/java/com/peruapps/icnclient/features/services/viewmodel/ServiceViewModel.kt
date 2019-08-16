package com.peruapps.icnclient.features.services.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.peruapps.icnclient.adapter.ServiceAdapter
import com.peruapps.icnclient.model.Service
import com.peruapps.icnclient.room.repository.OnServiceReadyCallback
import com.peruapps.icnclient.room.repository.ServiceRemoteDataSource

class ServiceViewModel: ViewModel() {

    var repository: ServiceRemoteDataSource = ServiceRemoteDataSource()

    var services = MutableLiveData<ArrayList<Service>>()

    fun loadServices() {
         repository.getServices()
//        repository.getServices(object: OnServiceReadyCallback {
//            override fun onDataReady(data: ArrayList<Service>) {
//                services.value = data
//            }
//        })
    }
}