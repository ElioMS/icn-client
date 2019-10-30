package com.peruapps.icnclient.features.nursing_staff.presentation

import android.util.Log
import androidx.databinding.ObservableField
import com.peruapps.icnclient.adapter.NursingStaffAdapter
import com.peruapps.icnclient.model.Service
import com.peruapps.icnclient.model.ServiceType
import com.peruapps.icnclient.ui.base.BaseViewModel

class NursingStaffViewModel : BaseViewModel<NursingStaffNavigator>() {
    val nursingAdapter = NursingStaffAdapter(arrayListOf()) { model, position ->
        nursingStaffSelectedItem(model)
    }

    var service = ObservableField<Service>()
    var serviceType = ObservableField<ServiceType>()

    private fun nursingStaffSelectedItem(model: ServiceType) {
        serviceType.set(model)
        getNavigator().showCalendarView()
    }

    fun setServiceTypeItems(data: ArrayList<ServiceType>, serviceEntity: Service) {
        nursingAdapter.bindItems(data)
        service.set(serviceEntity)
    }
}