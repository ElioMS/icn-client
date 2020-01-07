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
import com.peruapps.icnclient.features.summary.presentation.SummaryActivity
import com.peruapps.icnclient.helpers.NavigationHelper
import com.peruapps.icnclient.model.AppointmentDate
import com.peruapps.icnclient.model.Service
import com.peruapps.icnclient.model.ServiceType
import com.peruapps.icnclient.widgets.CustomCalendarView
import kotlinx.android.synthetic.main.fragment_service_type.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import androidx.lifecycle.Observer

class ServiceTypeFragment : Fragment(), CustomCalendarView.CustomCalendarListener, ServiceTypeNavigator {

    val TAG = ServiceTypeFragment::class.java.simpleName

    val model: ServiceTypeViewModel by viewModel()
    private lateinit var binding: FragmentServiceTypeBinding

    var list = ArrayList<ServiceType>()
    private lateinit var spinner: Spinner

    lateinit var service: Service
    var days = ArrayList<AppointmentDate>()
    private var calendarView : CustomCalendarView? = null

    private var hour: Int = 0
    private var minute: Int = 0


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

        val calendar = Calendar.getInstance()
        calendar.add(Calendar.HOUR, 4)
        hour = calendar.get(Calendar.HOUR_OF_DAY)
        minute = calendar.get(Calendar.MINUTE)

        model.setServiceTypeItems(list, service)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_service_type, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model.setNavigator(this)
        binding.setVariable(BR.viewModel, model)
        subscribeLiveData()
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


            val calendar = Calendar.getInstance()
            calendar.add(Calendar.HOUR, 4)

            val datetime = Calendar.getInstance()
            datetime.set(Calendar.HOUR_OF_DAY, h)
            datetime.set(Calendar.MINUTE, m)

            if (datetime.timeInMillis < calendar.timeInMillis) {
                Toast.makeText(context!!, "Horario de atención no disponible", Toast.LENGTH_LONG).show()
            } else {
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
                val hour = "$h:$m"
                model.hour.set(hour)
            }

        }),hour, minute,false)
        tpDialog.show()
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
                    R.id.main_container, CalendarFragment.setData(1, service, model.serviceType.get()), "CalendarFragment")
            }
            "SUBSTANCES" -> {
                NavigationHelper.changeFragment(fragmentManager!!,
                    R.id.main_container, SubstancesFragment.setData(1, service, model.serviceType.get()!!), "SubstancesFragment")
            }
            "SCHEDULE" -> {
                NavigationHelper.changeFragment(fragmentManager!!,
                R.id.main_container, ScheduleDatesFragment.setData(1, service, model.serviceType.get(), days, 1), "ScheduleDatesFragment")
            }
            "SUMMARY" -> {
                NavigationHelper.redirectTo(activity!!, SummaryActivity::class.java)
            }
        }
    }

    private fun subscribeLiveData() {
        model.validationMessage.observe(this, Observer {
            Toast.makeText(context!!, it, Toast.LENGTH_SHORT).show()
        })
    }
}
