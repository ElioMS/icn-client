package com.peruapps.icnclient.room.repository

import android.os.Handler
import com.peruapps.icnclient.model.Appointment

class ReservationRemoteDataSource: ReservationRepository.Remote {

    override fun getTodayAppointments(onAppointmentReadyCallback: OnAppointmentReadyCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCulminatedAppointments(onAppointmentReadyCallback: OnAppointmentReadyCallback) {
        val arrayList = ArrayList<Appointment>()
        arrayList.add(Appointment("bbb"))
        arrayList.add(Appointment("aaaa"))
        Handler().postDelayed({ onAppointmentReadyCallback.onDataReady(arrayList) },2000)
    }

    override fun getScheduledAppointments(onAppointmentReadyCallback: OnAppointmentReadyCallback) {
        val arrayList = ArrayList<Appointment>()
        arrayList.add(Appointment("ccc"))
        arrayList.add(Appointment("ddd"))
        Handler().postDelayed({ onAppointmentReadyCallback.onDataReady(arrayList) },2000)
    }
}

interface OnAppointmentReadyCallback {
    fun onDataReady(data: ArrayList<Appointment>)
}