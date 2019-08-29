package com.peruapps.icnclient.features.services.views.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.peruapps.icnclient.BR
import com.peruapps.icnclient.R
import com.peruapps.icnclient.databinding.FragmentScheduleDatesBinding
import com.peruapps.icnclient.features.services.viewmodel.ServiceViewModel
import com.peruapps.icnclient.model.AppointmentDate

class ScheduleDatesFragment : Fragment() {

    private lateinit var model: ServiceViewModel
    private lateinit var binding: FragmentScheduleDatesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = activity?.run {
            ViewModelProviders.of(this)[ServiceViewModel::class.java]
        } ?: throw Exception("Invalid Activity")

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
        val timePicker = view.findViewById(R.id.timePicker) as TimePicker

        timePicker.setOnTimeChangedListener { view, hourOfDay, minute -> updateDisplay(hourOfDay, minute) }
    }

    private fun subscribeToLiveData() {
        model.scheduledDates.observe(this, Observer<ArrayList<AppointmentDate>> {
            model.addDates(it)
        })
    }

    fun updateDisplay(hourOfDay: Int, minute: Int) {

        model.scheduledDates.observe(this, Observer<ArrayList<AppointmentDate>> {
            it[model.selectedAppointmentDate.get()!!].hour = "${hourOfDay}:${minute}"
            model.addDates(it)
        })

        Log.d("timepicker", model.selectedAppointmentDate.get().toString())
    }
}
