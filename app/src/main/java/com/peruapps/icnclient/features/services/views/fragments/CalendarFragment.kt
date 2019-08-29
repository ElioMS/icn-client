package com.peruapps.icnclient.features.services.views.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.peruapps.icnclient.BR

import com.peruapps.icnclient.R
import com.peruapps.icnclient.databinding.FragmentCalendarBinding
import com.peruapps.icnclient.features.services.viewmodel.ServiceViewModel
import com.peruapps.icnclient.model.AppointmentDate
import com.peruapps.icnclient.widgets.CustomCalendarView
import kotlinx.android.synthetic.main.fragment_calendar.*
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.util.*

class CalendarFragment : Fragment(), CustomCalendarView.CustomCalendarListener {

    private lateinit var model: ServiceViewModel
    private lateinit var binding: FragmentCalendarBinding
    private lateinit var spMonths: Spinner
    private lateinit var spTurns: Spinner

    private var calendarView : CustomCalendarView? = null

    private var days = ArrayList<AppointmentDate>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        model = activity?.run {
            ViewModelProviders.of(this)[ServiceViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        model.showTimePicker.set(false)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calendar, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        spMonths = view.findViewById(R.id.sp_schedule)
        val monthAdapter = ArrayAdapter.createFromResource(context!!, R.array.schedule_types, R.layout.item_simple_spinner)
            .also {
                it.setDropDownViewResource(R.layout.item_simple_spinner)
            }
        spMonths.adapter = monthAdapter

        spTurns = view.findViewById(R.id.sp_turn)
        val turnAdapter = ArrayAdapter.createFromResource(context!!, R.array.turn_list, R.layout.item_simple_spinner)
            .also {
                it.setDropDownViewResource(R.layout.item_simple_spinner)
            }
        spTurns.adapter = turnAdapter

        binding.setVariable(BR.viewModel, model)

        initView()
    }

    private fun initView() {
        calendarView = customCalendarView?.also {
            it.setDayClickedListener(this)
        }
        /*customCalendarView.selec*/
    }

    override fun onDayClicked(date: Date) {
        val formatDate = SimpleDateFormat("yyy-MM-dd").format(date)
        // Get the week day with spanish locale
        val spanish =  Locale("es", "ES")
        val weekdayName = SimpleDateFormat("EEEE dd", spanish).format(date)

        val appointmentDate = AppointmentDate(weekdayName.capitalize(),"", formatDate)

        if (!days.contains(appointmentDate)) {
            days.add(appointmentDate)
        } else {
            days.remove(appointmentDate)
        }

        days.sortBy { selector(it) }
        model.scheduledDates.value = days
        Log.d("click_day", model.scheduledDates.value.toString())
    }

    fun selector(p: AppointmentDate): String = p.date
}
