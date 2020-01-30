package com.peruapps.icnclient.features.schedule_dates.presentation

import android.util.Log
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.peruapps.icnclient.R
import com.peruapps.icnclient.adapter.AppointmentAdapter
import com.peruapps.icnclient.model.AppointmentDate
import com.peruapps.icnclient.model.Service
import com.peruapps.icnclient.model.ServiceType
import com.peruapps.icnclient.room.entity.PersonalTable
import com.peruapps.icnclient.room.entity.ServiceDetail
import com.peruapps.icnclient.room.repository.PersonalTableRepository
import com.peruapps.icnclient.room.repository.ServiceDetailRepository
import com.peruapps.icnclient.ui.base.BaseViewModel

class ScheduleDatesViewModel(
    private val serviceDetailRepository: ServiceDetailRepository,
    private val personalTableRepository: PersonalTableRepository
) : BaseViewModel<ScheduleDatesNavigator>() {

    val appointmentAdapter = AppointmentAdapter(arrayListOf()) { model, position ->
        setHour(model, position)
    }

    val service = ObservableField<Service>()
    val serviceType = ObservableField<ServiceType>()

    var categoryId = ObservableField<Int>()
    val selectedAppointmentDate = ObservableField(0)

    val scheduledDates = MutableLiveData<ArrayList<AppointmentDate>>()

    var nursesCount = ObservableInt(1)
    var techniciansCount = ObservableInt(1)
    var price = ObservableField<Float>()

    private val _validationMessage = MutableLiveData<Int>()
    val validationMessage: LiveData<Int>
        get() = _validationMessage

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

        val hours = appointmentAdapter.items.map { it.stringHour }

        if (hours.contains("")) {
            _validationMessage.value = R.string.validation_empty
        } else {
            saveAppointmentsDatabase()
            getNavigator().showSummaryView()
        }
    }

    fun createPersonalAppointment() {
        val turns = appointmentAdapter.items.map { it.turnToString() }

        if (turns.contains("")) {
            _validationMessage.value = R.string.validation_empty
        } else {

            startJob {

                val price = serviceType.get()?.let { it.price!! * appointmentAdapter.items.size } ?: run { service.get()!!.price * appointmentAdapter.items.size }

                val id = serviceDetailRepository.insert(
                    ServiceDetail(
                        serviceId = service.get()!!.id,
                        serviceName = service.get()!!.name,
                        serviceTypeId = serviceType.get()?.id,
                        serviceTypeName = serviceType.get()?.name,
                        price = price
                    )
                )

                val itemPrice = price / appointmentAdapter.items.size

                appointmentAdapter.items.forEach { x ->
                    personalTableRepository.insert(
                        PersonalTable(
                            hour = "",
                            stringHour = "",
                            date = x.date,
                            stringDate = x.string_date,
                            turn = x.turn,
                            quantity = if (service.get()!!.id == 5) nursesCount.get() else techniciansCount.get(),
                            price = itemPrice,
                            detailId = id.toInt()
                        )
                    )
                }

                getNavigator().showSummaryView()
            }
        }
    }

    private fun saveAppointmentsDatabase() {
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
    }

    fun incrementValue(value: Int) {
        when (value) {
            0 -> {
                val nurses = nursesCount.get()

                if (nurses < 2) {
                    nursesCount.set(nursesCount.get().plus(1))
                    scheduledDates.value!![selectedAppointmentDate.get()!!].nurses = nursesCount.get()
                } else {
                    _validationMessage.value = R.string.validation_max_personal
                }
            }
            else -> {
                val technicians = techniciansCount.get()

                if (technicians < 2) {
                    techniciansCount.set(techniciansCount.get().plus(1))
                    scheduledDates.value!![selectedAppointmentDate.get()!!].technicians = techniciansCount.get()
                } else {
                    _validationMessage.value = R.string.validation_max_personal
                }
            }
        }


    }

    fun decrementValue(value: Int) {
        when (value) {
            0 -> {
                val nurses = nursesCount.get()

                if (nurses > 1) {
                    nursesCount.set(nursesCount.get().minus(1))
                    scheduledDates.value!![selectedAppointmentDate.get()!!].nurses = nursesCount.get()
                } else {
                    _validationMessage.value = R.string.validation_min_personal
                }
            }
            else -> {
                if (techniciansCount.get() > 1) {
                    techniciansCount.set(techniciansCount.get().minus(1))
                    scheduledDates.value!![selectedAppointmentDate.get()!!].technicians = techniciansCount.get()
                } else {
                    _validationMessage.value = R.string.validation_min_personal
                }
            }
        }
    }
}