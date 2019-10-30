package com.peruapps.icnclient.features.reservations.data

import com.peruapps.icnclient.model.Appointment
import com.peruapps.icnclient.model.response.AppointmentResponse

interface AppointmentRepository {
    suspend fun listUserAppointments(categoryId: Int, type: Int): List<Appointment>
}