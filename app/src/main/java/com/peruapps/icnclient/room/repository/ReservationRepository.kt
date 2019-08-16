package com.peruapps.icnclient.room.repository

interface ReservationRepository {

    interface Remote {
        fun getTodayAppointments(onAppointmentReadyCallback: OnAppointmentReadyCallback)

        fun getCulminatedAppointments(onAppointmentReadyCallback: OnAppointmentReadyCallback)

        fun getScheduledAppointments(onAppointmentReadyCallback: OnAppointmentReadyCallback)
    }

}