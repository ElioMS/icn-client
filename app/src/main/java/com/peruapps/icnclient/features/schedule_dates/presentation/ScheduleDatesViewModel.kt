package com.peruapps.icnclient.features.schedule_dates.presentation

import android.util.Log
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.peruapps.icnclient.adapter.AppointmentAdapter
import com.peruapps.icnclient.model.AppointmentDate
import com.peruapps.icnclient.model.Service
import com.peruapps.icnclient.model.ServiceType
import com.peruapps.icnclient.room.entity.ServiceDetail
import com.peruapps.icnclient.room.repository.ServiceDetailRepository
import com.peruapps.icnclient.ui.base.BaseViewModel

class ScheduleDatesViewModel(private val serviceDetailRepository: ServiceDetailRepository): BaseViewModel<ScheduleDatesNavigator>() {

    val appointmentAdapter = AppointmentAdapter(arrayListOf()) {
            model, position -> setHour(model, position)
    }

    val service = ObservableField<Service>()
    val serviceType = ObservableField<ServiceType>()

    var categoryId = ObservableField<Int>()
    val selectedAppointmentDate = ObservableField<Int>()

    val scheduledDates = MutableLiveData<ArrayList<AppointmentDate>>()

    var nursesCount = ObservableInt(0)
    var techniciansCount = ObservableInt(0)

    private fun setHour(model: AppointmentDate, position: Int) {
        selectedAppointmentDate.set(position)
        val appointment = scheduledDates.value!![position]

        nursesCount.set(appointment.nurses)
        techniciansCount.set(appointment.technicians)
    }

    fun addDates(result: ArrayList<AppointmentDate>) {
        appointmentAdapter.bindItems(result)
    }

    fun createAppointment() {
        startJob {
            serviceDetailRepository.insert(
                ServiceDetail(
                    serviceId = service.get()!!.id,
                    serviceName = service.get()!!.name,
                    serviceTypeId = serviceType.get()?.id,
                    serviceTypeName = serviceType.get()?.name,
                    price = serviceType.get()?.let { it.price } ?: run { service.get()!!.price }
                )
            )
        }
        getNavigator().showSummaryView()
    }

    fun incrementValue(value: Int) {
        when(value) {
            0 ->{
                nursesCount.set(nursesCount.get().plus(1))
                scheduledDates.value!![selectedAppointmentDate.get()!!].nurses = nursesCount.get()
            }
            else -> {
                techniciansCount.set(techniciansCount.get().plus(1))
                scheduledDates.value!![selectedAppointmentDate.get()!!].technicians = techniciansCount.get()
            }
        }



    }

    fun decrementValue(value: Int) {
        when(value) {
            0 -> {
                if (nursesCount.get() > 0) {
                    nursesCount.set(nursesCount.get().minus(1))
                    scheduledDates.value!![selectedAppointmentDate.get()!!].nurses = nursesCount.get()
                }
            }
            else -> {
                if (techniciansCount.get() > 0) {
                    techniciansCount.set(techniciansCount.get().minus(1))
                    scheduledDates.value!![selectedAppointmentDate.get()!!].technicians = techniciansCount.get()
                }
            }
        }
    }
}