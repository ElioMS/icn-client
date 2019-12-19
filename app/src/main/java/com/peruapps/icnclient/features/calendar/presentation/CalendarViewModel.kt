package com.peruapps.icnclient.features.calendar.presentation

import android.util.Log
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import com.peruapps.icnclient.model.AppointmentDate
import com.peruapps.icnclient.model.Service
import com.peruapps.icnclient.model.ServiceType
import com.peruapps.icnclient.ui.base.BaseViewModel

class CalendarViewModel: BaseViewModel<CalendarNavigator>() {

    var categoryId = ObservableField<Int>()

    val scheduledDates = MutableLiveData<ArrayList<AppointmentDate>>()

    val selectedScheduleType = ObservableField(0)

    var nursesCount = ObservableInt(0)
    var techniciansCount = ObservableInt(0)

    val service = ObservableField<Service>()
    val serviceType = ObservableField<ServiceType>()
    var price = ObservableField<Float>(0f)

    fun calendarButtonEvent() {
        if (categoryId.get()!! == 1) {
            getNavigator().showNextView("DAY")
        } else {
            if (selectedScheduleType.get() == 0) {
                getNavigator().showNextView("DAY")
            } else {
                getNavigator().showNextView("SUMMARY")
            }
        }
    }

    fun incrementValue(value: Int) {
        when(value) {
            0 -> nursesCount.set(nursesCount.get().plus(1))
            else -> techniciansCount.set(techniciansCount.get().plus(1))
        }

        Log.d("count", nursesCount.get().toString())
    }

    fun decrementValue(value: Int) {
        when(value) {
            0 -> {
                if (nursesCount.get() > 0) {
                    nursesCount.set(nursesCount.get().minus(1))
                }
            }
            else -> {
                if (techniciansCount.get() > 0) {
                    techniciansCount.set(techniciansCount.get().minus(1))
                }
            }
        }
    }

}