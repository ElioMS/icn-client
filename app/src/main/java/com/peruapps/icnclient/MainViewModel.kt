package com.peruapps.icnclient

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.peruapps.icnclient.data.*
import com.peruapps.icnclient.model.Service

class MainViewModel: ViewModel() {

    var repoModel: RepoModel = RepoModel()
    var serviceModel: ServiceModel = ServiceModel()

    val text = ObservableField("old data")
    val isLoading = ObservableField(false)

    var services = MutableLiveData<ArrayList<Service>>()

    fun refresh(){
        isLoading.set(true)
        repoModel.refreshData(object : OnDataReadyCallback {
            override fun onDataReady(data: String) {
                isLoading.set(false)
                text.set(data)
            }
        })
    }

    fun loadServices() {
        serviceModel.getServices(object: OnServicesReadyCallback {
            override fun onDataReady(data: ArrayList<Service>) {
                services.value = data
            }
        })
    }
}