package com.peruapps.icnclient.features.schedule_dates.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TimePicker
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.peruapps.icnclient.BR
import com.peruapps.icnclient.R
import com.peruapps.icnclient.databinding.FragmentScheduleDatesBinding
import com.peruapps.icnclient.extensions.onItemSelected
import com.peruapps.icnclient.features.summary.presentation.SummaryActivity
import com.peruapps.icnclient.helpers.NavigationHelper
import com.peruapps.icnclient.model.AppointmentDate
import com.peruapps.icnclient.model.Service
import com.peruapps.icnclient.model.ServiceType
import org.koin.androidx.viewmodel.ext.android.viewModel

class ScheduleDatesFragment : Fragment(), ScheduleDatesNavigator {

    val TAG = ScheduleDatesFragment::class.java.simpleName

    val model: ScheduleDatesViewModel by viewModel()
    private lateinit var binding: FragmentScheduleDatesBinding
    private lateinit var spTurns: Spinner

    /**
     * Current category selected
     */
    var currentCategory = 1

    /**
     * This could be applied to all days or by days in CANALIZACION FLOW
     */
    var scheduleType: Int? = null


    /**
     * Dates selected from calendar or service type view
     */
    var selectedDatesList = ArrayList<AppointmentDate>()

    private lateinit var currentService: Service
    private var currentServiceType: ServiceType? = null


    companion object {
        fun setData(category: Int, service: Service, serviceType: ServiceType? = null, dates: ArrayList<AppointmentDate>, scheduleType: Int?) = ScheduleDatesFragment().apply {
            this.selectedDatesList = dates
            this.currentCategory = category

            Log.d(TAG, service.toString())
            Log.d(TAG, serviceType.toString())

            this.currentService = service

            serviceType?.let { this.currentServiceType = it }
            scheduleType?.let { this.scheduleType = it }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        subscribeToLiveData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_schedule_dates, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setVariable(BR.viewModel, model)
        model.setNavigator(this)

        model.scheduledDates.value = selectedDatesList
        model.categoryId.set(currentCategory)
        model.addDates(selectedDatesList)
        model.service.set(currentService)
        model.serviceType.set(currentServiceType)

        val timePicker = view.findViewById(R.id.timePicker) as TimePicker

        spTurns = view.findViewById(R.id.sp_turn)
        val turnAdapter = ArrayAdapter.createFromResource(context!!, R.array.turn_list, R.layout.item_simple_spinner)
            .also {
                it.setDropDownViewResource(R.layout.item_simple_spinner)
            }
        spTurns.adapter = turnAdapter

//
        spTurns.onItemSelected { position ->
            val itemSelected = model.selectedAppointmentDate.get()
            itemSelected?.let {
                model.appointmentAdapter.items[it].turn = position
                model.appointmentAdapter.notifyDataSetChanged()
            }
        }

        timePicker.setOnTimeChangedListener { view, hourOfDay, minute -> updateDisplay(hourOfDay, minute) }
    }

    private fun subscribeToLiveData() {

    }

    private fun updateDisplay(hourOfDay: Int, minute: Int) {
        val min = when {
            minute < 10 ->  "0$minute"
            else -> "$minute"
        }

        var meridiem = "AM"
        var hour = hourOfDay

        when {
            hour > 12 -> {
                hour = hour.minus(12)
                meridiem = "PM"
            }
            hour == 12 -> {
                meridiem = "PM"
            }
        }

        when (scheduleType) {
            0 -> {
                model.appointmentAdapter.items[model.selectedAppointmentDate.get()!!].hour = "$hourOfDay:$min"
                model.appointmentAdapter.items[model.selectedAppointmentDate.get()!!].stringHour = "$hourOfDay:$min $meridiem"
                model.appointmentAdapter.notifyDataSetChanged()
            }
            else -> {
                model.appointmentAdapter.items.forEach {
                    it.hour = "$hourOfDay:$min"
                    it.stringHour = "$hour:$min $meridiem"
                }
                model.appointmentAdapter.notifyDataSetChanged()
            }
        }

//        model.scheduledDates.observe(this, Observer<ArrayList<AppointmentDate>> {
//            it[model.selectedAppointmentDate.get()!!].hour = "$hourOfDay:$min"
//            model.addDates(it)
//        })
    }

    override fun showSummaryView() {

        NavigationHelper.redirectTo(activity!!, SummaryActivity::class.java)

    }

}
