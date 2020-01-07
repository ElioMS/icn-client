package com.peruapps.icnclient.features.service_type.presentation

import android.util.Log
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.peruapps.icnclient.R
import com.peruapps.icnclient.adapter.ItemServiceTypeAdapter
import com.peruapps.icnclient.model.Service
import com.peruapps.icnclient.model.ServiceType
import com.peruapps.icnclient.room.entity.ServiceDetail
import com.peruapps.icnclient.room.repository.ServiceDetailRepository
import com.peruapps.icnclient.ui.base.BaseViewModel

class ServiceTypeViewModel (private val serviceDetailRepository: ServiceDetailRepository) : BaseViewModel<ServiceTypeNavigator>() {

    var service = ObservableField<Service>()
    var serviceType = ObservableField<ServiceType>()
    var serviceTypePosition = ObservableField(0)
    var showSubstances = ObservableField<Boolean>(false)
    val showTimePicker = ObservableField<Boolean>(false)
    val substanceQuantity = ObservableInt(0)
    val serviceTypesCount = ObservableField(0)
    val hour = ObservableField("")

    private val _validationMessage = MutableLiveData<Int>()
    val validationMessage : LiveData<Int>
        get() = _validationMessage

    val itemServiceTypeAdapter = ItemServiceTypeAdapter(arrayListOf()) { model, position ->
        checkSelectedServiceType(model, position)
    }

    fun setServiceTypeItems(data: ArrayList<ServiceType>, serviceEntity: Service) {
        itemServiceTypeAdapter.bindItems(data)
        serviceTypesCount.set(itemServiceTypeAdapter.itemCount)
        service.set(serviceEntity)
    }

    fun changeHasMaterialStatus(value: Boolean) {
        hasMaterial.set(value)

        if (!value) {
            getNavigator().showContactUsModelDialog()
            doSchedule.set(false)
        } else {
            showTimePicker.set(false)
            doSchedule.set(true)
        }
    }

    fun changeDoScheduleStatus(value: Boolean) {
        doSchedule.set(value)

        if (!value) {
            showTimePicker.set(true)
            selectScheduleType.set(false)
        } else {
            showTimePicker.set(false)
        }
    }

    fun showTimePickerDialog() {
        getNavigator().showTimePickerDialog()
    }

    fun nextButtonEvents() {

        val serviceId = service.get()!!.id
        val scheduleValue = doSchedule.get()
        val position = serviceTypePosition.get()

        if (serviceId == 1 && position == 1 || serviceId == 1 && position == 2) {
            getNavigator().showNextView("SUBSTANCES")
        }
//
        if (serviceId == 1 && position == 0 || serviceId == 3) {
            when (scheduleValue) {
                true -> {
                    if (serviceType.get() == null) {
                        _validationMessage.value = R.string.validation_service_type
                    } else {
                        getNavigator().showNextView("CALENDAR")
                    }
                }
                false -> {
                    if  (hour.get() != "" && serviceType.get() != null) {
                        registerFlow()
                    } else if (serviceType.get() == null) {
                        _validationMessage.value = R.string.validation_service_type
                    } else if (hour.get() == "") {
                        _validationMessage.value = R.string.validation_service_type_hour
                    }
                }
            }
        }

        if (serviceId == 2) {
            when (scheduleValue) {
                true -> getNavigator().showNextView("CALENDAR")
                false -> {

                    if (hour.get() == "") {
                        _validationMessage.value = R.string.validation_service_type_hour
                    } else {
                        startJob {
                            serviceDetailRepository.insert(
                                ServiceDetail(
                                    serviceId = service.get()!!.id,
                                    serviceName = service.get()!!.name,
                                    serviceTypeId = null,
                                    serviceTypeName = null,
                                    price = service.get()!!.price!!
                                )
                            )
                        }

                        getNavigator().showNextView("SUMMARY")
                    }
                }
            }
        }
    }

    private fun registerFlow() {
        startJob {
            serviceDetailRepository.insert(
                ServiceDetail(
                    serviceId = service.get()!!.id,
                    serviceName = service.get()!!.name,
                    serviceTypeId = serviceType.get()!!.id,
                    serviceTypeName = serviceType.get()!!.name,
                    price = serviceType.get()!!.price!!,
                    hour = hour.get()
                )
            )

            getNavigator().showNextView("SUMMARY")
        }
    }

    /**
     * Allows to manage layout service types events in endovenoso flow
     */
    private fun checkSelectedServiceType(model: ServiceType, position: Int) {
//        Log.d("service_type", "${service.get()!!.id} ${model.name} $position")
        val serviceId = service.get()!!.id
        serviceType.set(model)
        serviceTypePosition.set(position)

        if (serviceId == 1 && position == 1) {
            hasMaterial.set(true)
            doSchedule.set(false)
            showTimePicker.set(false)
            showSubstances.set(true)
        } else if (serviceId == 1 && position == 2) {
            hasMaterial.set(true)
            doSchedule.set(false)
            showSubstances.set(true)
        } else if (serviceId == 1 && position == 0) {
            hasMaterial.set(false)
            doSchedule.set(false)
            showTimePicker.set(false)
            showSubstances.set(false)
        }
    }


    fun incrementSubstances() {
        val quantity = substanceQuantity.get()
        substanceQuantity.set(quantity.plus(1))
    }

    fun decrementSubstances() {
        val quantity = substanceQuantity.get()

        if (quantity > 0) {
            substanceQuantity.set(quantity.minus(1))
        }
    }

}