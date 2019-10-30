package com.peruapps.icnclient.features.calendar.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import com.peruapps.icnclient.BR
import com.peruapps.icnclient.R
import com.peruapps.icnclient.databinding.FragmentCalendarBinding
import com.peruapps.icnclient.features.schedule_dates.presentation.ScheduleDatesFragment
import com.peruapps.icnclient.features.summary.presentation.SummaryFragment
import com.peruapps.icnclient.helpers.NavigationHelper
import com.peruapps.icnclient.model.AppointmentDate
import com.peruapps.icnclient.model.Service
import com.peruapps.icnclient.model.ServiceType
import com.peruapps.icnclient.widgets.CustomCalendarView
import kotlinx.android.synthetic.main.fragment_calendar.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class CalendarFragment : Fragment(), CustomCalendarView.CustomCalendarListener, CalendarNavigator {

    val model: CalendarViewModel by viewModel()

    private lateinit var binding: FragmentCalendarBinding

    private var calendarView : CustomCalendarView? = null

    private var days = ArrayList<AppointmentDate>()

    private lateinit var spSchedule: Spinner
    private lateinit var spTurns: Spinner

    var currentCategory = 1


    private lateinit var service: Service
    private lateinit var serviceType: ServiceType

    companion object {
        fun setData(category: Int, service: Service, serviceType: ServiceType) = CalendarFragment().apply {
            Log.d("calendar_view", "$category $service $serviceType")
            this.currentCategory = category
            this.service = service
            this.serviceType = serviceType
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
        model.categoryId.set(currentCategory)
        binding.setVariable(BR.viewModel, model)

        calendarView = customCalendarView?.also {
            it.setDayClickedListener(this)
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
        Log.d("click_day", model.scheduledDates.value.toString())
    }

    private fun selector(p: AppointmentDate): String = p.date

    override fun showNextView(viewName: String) {
        when (viewName) {
            "DAY" -> {
                NavigationHelper.changeFragment(fragmentManager!!,
                    R.id.main_container,
                    ScheduleDatesFragment.setData(this.currentCategory, service, serviceType, this.days, model.selectedScheduleType.get()),
                    "ScheduleDatesFragment")
            }
            "SUMMARY" -> {
                NavigationHelper.changeFragment(fragmentManager!!,
                    R.id.main_container,
                    SummaryFragment(),
                    "SummaryFragment")
            }
        }
    }
}
