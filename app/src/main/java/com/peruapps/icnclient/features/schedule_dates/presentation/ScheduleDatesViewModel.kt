package com.peruapps.icnclient.features.schedule_dates.presentation

import android.util.Log
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.peruapps.icnclient.adapter.AppointmentAdapter
import com.peruapps.icnclient.model.AppointmentDate
import com.peruapps.icnclient.ui.base.BaseViewModel

class ScheduleDatesViewModel: BaseViewModel<ScheduleDatesNavigator>() {

    val appointmentAdapter = AppointmentAdapter(arrayListOf()) {
            model, position -> setHour(model, position)
    }

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