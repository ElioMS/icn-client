package com.peruapps.icnclient.features.reservations.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.peruapps.icnclient.model.Appointment
import com.peruapps.icnclient.room.repository.OnAppointmentReadyCallback
import com.peruapps.icnclient.room.repository.ReservationRemoteDataSource

class ReservationViewModel: ViewModel() {

    var repository: ReservationRemoteDataSource = ReservationRemoteDataSource()

    var appointments = MutableLiveData<ArrayList<Appointment>>()
    val isLoading = ObservableField(false)

    fun loadScheduledAppointments() {
        isLoading.set(true)
        repository.getScheduledAppointments(object: OnAppointmentReadyCallback {
            override fun onDataReady(data: ArrayList<Appointment>) {
                appointments.value = data
                isLoading.set(false)
            }
        })
    }

    fun loadCulminatedAppointments() {
        repository.getCulminatedAppointments(object: OnAppointmentReadyCallback {
            override fun onDataReady(data: ArrayList<Appointment>) {
                appointments.value = data
            }
        })
    }

}