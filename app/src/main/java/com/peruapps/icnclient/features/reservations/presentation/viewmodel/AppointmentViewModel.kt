package com.peruapps.icnclient.features.reservations.presentation.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.peruapps.icnclient.model.Appointment

class AppointmentViewModel: ViewModel() {

    var appointments = MutableLiveData<ArrayList<Appointment>>()
    val isLoading = ObservableField(false)


}