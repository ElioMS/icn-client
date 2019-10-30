package com.peruapps.icnclient.features.reservations.data

import com.peruapps.icnclient.model.Appointment
import com.peruapps.icnclient.model.response.AppointmentResponse
import com.peruapps.icnclient.network.ApiService

class AppointmentRepositoryImpl(private val apiService: ApiService): AppointmentRepository {

    override suspend fun listUserAppointments(categoryId: Int, type: Int): List<Appointment> {
        return apiService.appointments(categoryId, type)
    }
}