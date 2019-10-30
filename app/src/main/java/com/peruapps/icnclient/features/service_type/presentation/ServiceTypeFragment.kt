package com.peruapps.icnclient.features.service_type.presentation

import android.app.TimePickerDialog
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
import com.peruapps.icnclient.databinding.FragmentServiceTypeBinding
import com.peruapps.icnclient.dialogs.CustomDialogContactUs
import com.peruapps.icnclient.features.calendar.presentation.CalendarFragment
import com.peruapps.icnclient.features.schedule_dates.presentation.ScheduleDatesFragment
import com.peruapps.icnclient.features.substances.presentation.SubstancesFragment
import com.peruapps.icnclient.features.summary.presentation.SummaryFragment
import com.peruapps.icnclient.helpers.NavigationHelper
import com.peruapps.icnclient.model.Appointment
import com.peruapps.icnclient.model.AppointmentDate
import com.peruapps.icnclient.model.Service
import com.peruapps.icnclient.model.ServiceType
import com.peruapps.icnclient.widgets.CustomCalendarView
import kotlinx.android.synthetic.main.fragment_calendar.*
import kotlinx.android.synthetic.main.fragment_service_type.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ServiceTypeFragment : Fragment(), CustomCalendarView.CustomCalendarListener, ServiceTypeNavigator {

    val model: ServiceTypeViewModel by viewModel()
    private lateinit var binding: FragmentServiceTypeBinding

    var list = ArrayList<ServiceType>()
    private lateinit var spinner: Spinner

    lateinit var service: Service
    var days = ArrayList<AppointmentDate>()
    private var calendarView : CustomCalendarView? = null


    companion object {
        fun newInstance(data: ArrayList<ServiceType>, service: Service) = ServiceTypeFragment().apply {
            this.list = data
            this.service = service
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        model.setServiceTypeItems(list, service)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_service_type, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        calendarView = calendar?.also {
            it.setDayClickedListener(this)
        }

        model.setNavigator(this)
        binding.setVariable(BR.viewModel, model)

        setSpinnerData()
    }

    override fun showContactUsModelDialog() {
        val dialog = CustomDialogContactUs(context!!, R.style.FullScreenDialogStyle)
        dialog.show()
    }

    override fun showTimePickerDialog() {
        openTimePickerDialog()
    }

    private fun openTimePickerDialog() {
        val tpDialog = TimePickerDialog(context!!, TimePickerDialog.OnTimeSetListener(function = { _, h, m ->

            val selectedHour: String
//
            if (h <= 12) {
                selectedHour = "$h:$m AM"
            } else {
                var hour = h

                if (h != 12) {
                    hour = h - 12
                }

                selectedHour = "$hour:$m PM"
            }

            tvHour.text = selectedHour

            Toast.makeText(context!!, selectedHour, Toast.LENGTH_LONG).show()

        }),11, 12,false)
        tpDialog.show()
    }

    private fun setSpinnerData() {
        spinner = view!!.findViewById(R.id.sp_schedule)
        val turnAdapter = ArrayAdapter.createFromResource(context!!, R.array.turn_list, R.layout.item_simple_spinner)
            .also {
                it.setDropDownViewResource(R.layout.item_simple_spinner)
            }
        spinner.adapter = turnAdapter
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
//        model.scheduledDates.value = days
//        Log.d("click_day", model.scheduledDates.value.toString())
    }

    private fun selector(p: AppointmentDate): String = p.date

    /**
     * @param viewName
     * Change fragments depends on the current values assigned to viewName
     */
    override fun showNextView(viewName: String) {
        when (viewName) {
            "CALENDAR" -> {
                NavigationHelper.changeFragment(fragmentManager!!,
                    R.id.main_container, CalendarFragment.setData(1, service, model.serviceType.get()!!), "CalendarFragment")
            }
            "SUBSTANCES" -> {
                NavigationHelper.changeFragment(fragmentManager!!,
                    R.id.main_container, SubstancesFragment.setData(1, service, model.serviceType.get()!!, model.substanceQuantity.get()), "SubstancesFragment")
            }
            "SCHEDULE" -> {
                NavigationHelper.changeFragment(fragmentManager!!,
                R.id.main_container, ScheduleDatesFragment.setData(1, service, model.serviceType.get(), days, 1), "ScheduleDatesFragment")
            }
            "SUMMARY" -> {
                NavigationHelper.changeFragment(fragmentManager!!,
                    R.id.main_container, SummaryFragment(), "SummaryFragment")
            }
        }
    }
}
