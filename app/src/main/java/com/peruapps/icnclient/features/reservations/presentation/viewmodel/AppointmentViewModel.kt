package com.peruapps.icnclient.features.reservations.presentation.viewmodel

import android.util.Log
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import com.peruapps.icnclient.adapter.ReservationAdapter
import com.peruapps.icnclient.features.reservations.data.AppointmentRepository
import com.peruapps.icnclient.features.reservations.presentation.views.AppointmentNavigator
import com.peruapps.icnclient.model.Appointment
import com.peruapps.icnclient.ui.base.BaseViewModel

class AppointmentViewModel(private val repository: AppointmentRepository) : BaseViewModel<AppointmentNavigator>() {

    var appointments = MutableLiveData<ArrayList<Appointment>>()
    val isLoading = ObservableField(false)

    val adapter = ReservationAdapter(arrayListOf())

    val categoryId = ObservableField(1)
    val optionPosition = ObservableInt(0)

    init {
        loadAppointments(1, 0)
    }

    fun setCategoryColor(position: Int) {
        categoryId.set(position)
        loadAppointments(position, optionPosition.get())
    }

    fun loadAppointments(categoryId: Int, type: Int) {
        startJob {
            val response = repository.listUserAppointments(categoryId, type)
            adapter.bindItems(ArrayList(response))
        }
    }
}