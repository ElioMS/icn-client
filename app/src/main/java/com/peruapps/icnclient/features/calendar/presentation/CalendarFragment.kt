package com.peruapps.icnclient.features.calendar.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.peruapps.icnclient.BR
import com.peruapps.icnclient.R
import com.peruapps.icnclient.databinding.FragmentCalendarBinding
import com.peruapps.icnclient.features.schedule_dates.presentation.ScheduleDatesFragment

import com.peruapps.icnclient.helpers.NavigationHelper
import com.peruapps.icnclient.model.AppointmentDate
import com.peruapps.icnclient.model.Service
import com.peruapps.icnclient.model.ServiceType
import com.peruapps.icnclient.widgets.CustomCalendarView
import kotlinx.android.synthetic.main.calendar_view_layout.*
import kotlinx.android.synthetic.main.fragment_calendar.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*
import androidx.lifecycle.Observer

class CalendarFragment : Fragment(), CustomCalendarView.CustomCalendarListener, CalendarNavigator {

    val model: CalendarViewModel by viewModel()

    private lateinit var binding: FragmentCalendarBinding

    private var calendarView : CustomCalendarView? = null

    private var days = ArrayList<AppointmentDate>()

    private lateinit var spSchedule: Spinner
    private lateinit var spTurns: Spinner

    var currentCategory = 1

    private lateinit var service: Service
    private var serviceType: ServiceType? = null

    private var calendarVisibility = true

    companion object {
        fun setData(category: Int, service: Service, serviceType: ServiceType? = null) = CalendarFragment().apply {
            this.currentCategory = category
            this.service = service
            serviceType?.let { this.serviceType = it }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calendar, container, false)
        this.days.clear()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model.setNavigator(this)
        model.price.set(0f)
        model.service.set(service)
        model.categoryId.set(currentCategory)
        model.serviceType.set(serviceType)
        binding.setVariable(BR.viewModel, model)

        calendarView = customCalendarView?.also {
            it.setDayClickedListener(this)
        }

        monthSpinner.setOnClickListener {
            if (calendarVisibility) {
                weekLayout.visibility = View.GONE
            } else {
                weekLayout.visibility = View.VISIBLE
            }

            calendarVisibility = !calendarVisibility
        }

        spSchedule = view.findViewById(R.id.sp_schedule)
        val monthAdapter = ArrayAdapter.createFromResource(context!!, R.array.schedule_types, R.layout.item_simple_spinner)
            .also {
                it.setDropDownViewResource(R.layout.item_simple_spinner)
            }
        spSchedule.adapter = monthAdapter

        spTurns = view.findViewById(R.id.sp_turn)
        val turnAdapter = ArrayAdapter.createFromResource(context!!, R.array.turn_list, R.layout.item_simple_spinner)
            .also {
                it.setDropDownViewResource(R.layout.item_simple_spinner)
            }
        spTurns.adapter = turnAdapter

        subscribeLiveData()
    }

    override fun onDayClicked(date: Date) {
        val formatDate = SimpleDateFormat("yyy-MM-dd").format(date)
        // Get the week day with spanish locale
        val spanish =  Locale("es", "ES")
        val weekdayName = SimpleDateFormat("EEEE dd", spanish).format(date)

        val appointmentDate = AppointmentDate(weekdayName.capitalize(),"", formatDate, 0,0, 1000)

        if (!days.contains(appointmentDate)) {
            days.add(appointmentDate)
        } else {
            days.remove(appointmentDate)
        }

        days.sortBy { selector(it) }
        model.scheduledDates.value = days

        var newPrice = 0f

        model.serviceType.get()?.let {
            newPrice = days.size * it.price!!
        } ?: run {
            newPrice = days.size * service.price
        }

        model.price.set(newPrice)
    }

    private fun selector(p: AppointmentDate): String = p.date

    override fun showNextView(viewName: String) {
        when (viewName) {
            "DAY" -> {
                NavigationHelper.changeFragment(fragmentManager!!,
                    R.id.main_container,
                    ScheduleDatesFragment.setData(
                        this.currentCategory,
                        service.apply { price = model.price.get()!! },
                        serviceType?.apply { price = model.price.get()!! },
                        this.days,
                        model.selectedScheduleType.get(),
                        model.price.get()),
                    "ScheduleDatesFragment")
            }
            "SUMMARY" -> {
//                NavigationHelper.changeFragment(fragmentManager!!,
//                    R.id.main_container,
//                    SummaryFragment(),
//                    "SummaryFragment")
            }
        }
    }

    private fun subscribeLiveData() {
        model.validationMessage.observe(this, Observer {
            Toast.makeText(context!!, it, Toast.LENGTH_SHORT).show()
        })
    }
}
