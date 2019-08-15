package com.peruapps.icnclient.data

import android.os.Handler
import com.peruapps.icnclient.model.request.Service


class ServiceModel {

    fun getServices(onServicesReadyCallback: OnServicesReadyCallback) {
        val arrayList = ArrayList<Service>()
        arrayList.add(Service(1, "bbb", ""))
        arrayList.add(Service(2, "aaaa", ""))
        Handler().postDelayed({ onServicesReadyCallback.onDataReady(arrayList) },2000)
    }

}

interface OnServicesReadyCallback {
    fun onDataReady(data: ArrayList<Service>)
}