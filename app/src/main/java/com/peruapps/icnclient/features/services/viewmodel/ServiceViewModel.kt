package com.peruapps.icnclient.features.services.viewmodel

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.peruapps.icnclient.R
import com.peruapps.icnclient.adapter.AppointmentAdapter
import com.peruapps.icnclient.adapter.ItemServiceTypeAdapter
import com.peruapps.icnclient.adapter.NursingStaffAdapter
import com.peruapps.icnclient.adapter.ServiceAdapter
import com.peruapps.icnclient.features.services.data.ServiceRepository
import com.peruapps.icnclient.features.services.views.ServicesNavigator
import com.peruapps.icnclient.model.AppointmentDate
import com.peruapps.icnclient.model.Service
import com.peruapps.icnclient.model.ServiceType
import com.peruapps.icnclient.ui.base.BaseViewModel
import kotlin.collections.ArrayList

class ServiceViewModel(private val repository: ServiceRepository): BaseViewModel<ServicesNavigator>() {

    val serviceAdapter = ServiceAdapter(arrayListOf()) {
        model, position -> showTypesByService(model, position)
    }




//    var repository: ServiceRemoteDataSource = ServiceRemoteDataSource()

    /**
     * category, set the category to load services
     */
    var category = ObservableField(0)

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


    init {
        doSchedule.set(true)
        hasMaterial.set(true)
        showTimePicker.set(false)
        showSubstances.set(false)
    }

    /**
     * Load services by category id
     */
    fun loadServices() {
        startJob {
            val result = repository.listServicesByCategory(category.get()!!)
            serviceAdapter.bindItems(ArrayList(result))
        }
    }

    private fun showTypesByService(entity: Service, position: Int) {
        if (position == 3) {
            getNavigator().showOthersDialog()
        } else {
            startJob {
                val response = repository.listItemsByService(entity.id)
                listServiceTypes.value = ArrayList(response)
                getNavigator().showServiceTypeView(ArrayList(response), entity)
            }
        }
    }



//    private fun nursingStaffSelectedItem(model: ServiceType) {
//        Log.d("nursing_staff", "${service.get()!!.id} ${model.name}")
//        serviceType.set(model)
//
//        getNavigator().showNextView("CALENDAR")
//    }
//
//    fun nextButtonEvents() {
//
//        val serviceId = service.get()!!.id
//        val scheduleValue = doSchedule.get()
//        val position = serviceTypePosition.get()
//
//        if (serviceId == 1 && position == 1) {
//            getNavigator().showNextView("SUBSTANCES")
//        }
//
//        if (serviceId == 1 && position != 1 || serviceId == 3) {
//            when(scheduleValue) {
//                true -> getNavigator().showNextView("CALENDAR")
//                false -> getNavigator().showNextView("SUMMARY")
//            }
//        }
//
//        if (serviceId == 2) {
//            when(scheduleValue) {
//                true -> getNavigator().showNextView("SCHEDULE")
//                false -> {
//                    doSchedule.set(true)
//                    showTimePicker.set(false)
//                }
//            }
//        }
//    }





}