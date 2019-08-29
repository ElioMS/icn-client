package com.peruapps.icnclient.features.services.viewmodel

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.peruapps.icnclient.R
import com.peruapps.icnclient.adapter.AppointmentAdapter
import com.peruapps.icnclient.adapter.ItemServiceTypeAdapter
import com.peruapps.icnclient.adapter.NursingStaffAdapter
import com.peruapps.icnclient.features.services.views.ServicesNavigator
import com.peruapps.icnclient.model.AppointmentDate
import com.peruapps.icnclient.model.Service
import com.peruapps.icnclient.model.ServiceType
import com.peruapps.icnclient.room.repository.ServiceRemoteDataSource
import com.peruapps.icnclient.ui.base.BaseViewModel
import kotlin.collections.ArrayList

class ServiceViewModel: BaseViewModel<ServicesNavigator>() {

    val adapter = ItemServiceTypeAdapter(arrayListOf()) {
            model, position -> checkSelectedServiceType(model, position)
    }

    val nursingAdapter = NursingStaffAdapter(arrayListOf()) {
        model, position -> nursingStaffSelectedItem(model)
    }

    val appointmentAdapter = AppointmentAdapter(arrayListOf()) {
        model, position -> setHour(model, position)
    }

    var repository: ServiceRemoteDataSource = ServiceRemoteDataSource()

    /**
     * category, set the category to load services
     */
    var category = ObservableField<Int>()

    /**
     * service Will decide which segments appears in the layout
     */
    var service = ObservableField<Service>()

    var serviceType = ObservableField<ServiceType>()
    var serviceTypePosition = ObservableField<Int>()

    /**
     *  listServices, the service variable will get his value from this list
     */
    var listServices = MutableLiveData<ArrayList<Service>>()

    val listServiceTypes = MutableLiveData<ArrayList<ServiceType>>()

    /**
     * show timepicker if true and hide if value equals to false
     */
    val showTimePicker = ObservableField<Boolean>()

    val showSubstances = ObservableField<Boolean>()

    /**
     * this value is belongs to calendar view,
     * decide if schedule is for all the days or it will assigned one by one
     */
    val selectedScheduleType = ObservableField<Int>()

    /**
     * value can be 0, 1 or 2
     */
    val selectedTurn = ObservableField<Int>()

    val scheduledDates = MutableLiveData<ArrayList<AppointmentDate>>()

//    val selectedAppointmentDate = MutableLiveData<AppointmentDate>()
    val selectedAppointmentDate = ObservableField<Int>()

    init {
        doSchedule.set(true)
        hasMaterial.set(true)
        showTimePicker.set(false)
        showSubstances.set(false)
    }

    /**
     * This will set the currentServiceType and listServiceTypes to manage service_type layout
     */
    fun checkService(entity: Service) {
        service.set(entity)
        listServiceTypes.value = data()
    }

    fun addServiceTypes(result: ArrayList<ServiceType>) {
        adapter.bindItems(result)
    }

    fun addNurseServiceTypes(result: ArrayList<ServiceType>) {
        nursingAdapter.bindItems(result)
    }

    fun addDates(result: ArrayList<AppointmentDate>) {
        appointmentAdapter.bindItems(result)
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

        if  (!value) {
            showTimePicker.set(true)
            selectScheduleType.set(false)
        } else {
            showTimePicker.set(false)
        }
    }

    /**
     * Allows to manage layout service types events in endovenoso flow
     */
    private fun checkSelectedServiceType(model: ServiceType, position: Int) {
        Log.d("service_type", "${service.get()!!.id} ${model.name} $position")
        val service = service.get()!!.id
        serviceType.set(model)
        serviceTypePosition. set(position)

        if (service == 1 && position == 1) {
            hasMaterial.set(false)
            doSchedule.set(false)
            showTimePicker.set(false)
            showSubstances.set(true)
        } else if (service == 1 && position != 1) {
            hasMaterial.set(true)
            doSchedule.set(true)
            showSubstances.set(false)
        }
    }

    private fun nursingStaffSelectedItem(model: ServiceType) {
        Log.d("nursing_staff", "${service.get()!!.id} ${model.name}")
        serviceType.set(model)

        getNavigator().showNextView("CALENDAR")
    }

    fun nextButtonEvents() {

        val serviceId = service.get()!!.id
        val scheduleValue = doSchedule.get()
        val position = serviceTypePosition.get()

        if (serviceId == 1 && position == 1) {
            getNavigator().showNextView("SUBSTANCES")
        }

        if (serviceId == 1 && position != 1 || serviceId == 3) {
            when(scheduleValue) {
                true -> getNavigator().showNextView("CALENDAR")
                false -> getNavigator().showNextView("SUMMARY")
            }
        }

        if (serviceId == 2) {
            when(scheduleValue) {
                true -> getNavigator().showNextView("SCHEDULE")
                false -> {
                    doSchedule.set(true)
                    showTimePicker.set(false)
                }
            }
        }
    }

    fun calendarButtonEvent() {
        if (selectedScheduleType.get() == 1) {
            getNavigator().showNextView("DAY")
        } else {
            showTimePicker.set(true)
        }
    }

    fun setHour(model: AppointmentDate, position: Int) {
        selectedAppointmentDate.set(position)
//        model.hour = "05:50"
    }

    /**
     * DATA FAKE
     */
    fun data(): ArrayList<ServiceType> {
        // TODO: Implement web services to load service type data
        val value = service.get()!!.id

        val arrayList = ArrayList<ServiceType>()

        when(value) {
            1 ->  { arrayList.add(ServiceType(1, "Canalización S/100", ""))
                    arrayList.add(ServiceType(2, "Canalización y administración",""))
                    arrayList.add(ServiceType(3, "Administración", "")) }

            3 -> {
                arrayList.add(ServiceType(1, "Sonda folei", ""))
                arrayList.add(ServiceType(2, "Sonda Naso Gástrica", ""))
            }

            5 -> {
                arrayList.add(ServiceType(1, "Servicio básico", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt" +
                        "ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut" +
                        "aliquip ex ea commodo consequat"))
                arrayList.add(ServiceType(2, "Servicio Medio", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt" +
                        "ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut" +
                        "aliquip ex ea commodo consequat"))
            }

            6 -> {
                arrayList.add(ServiceType(1, "Servicio básico", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt" +
                        "ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut" +
                        "aliquip ex ea commodo consequat"))
            }
        }

        return arrayList
    }

}