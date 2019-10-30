package com.peruapps.icnclient.features.services.views

import com.peruapps.icnclient.model.Service
import com.peruapps.icnclient.model.ServiceType

interface ServicesNavigator {
    fun showServiceTypeView(serviceTypes: ArrayList<ServiceType>, service: Service)
    fun showOthersDialog()
}
