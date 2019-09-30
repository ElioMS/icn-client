package com.peruapps.icnclient.features.services.views.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TimePicker
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.peruapps.icnclient.BR
import com.peruapps.icnclient.R
import com.peruapps.icnclient.databinding.FragmentScheduleDatesBinding
import com.peruapps.icnclient.extensions.onItemSelected
import com.peruapps.icnclient.features.services.viewmodel.ServiceViewModel
import com.peruapps.icnclient.model.AppointmentDate
import kotlinx.android.synthetic.main.fragment_schedule_dates.*
import kotlinx.android.synthetic.main.include_sp_turn_layout.*

class ScheduleDatesFragment : Fragment() {

    private lateinit var model: ServiceViewModel
    private lateinit var binding: FragmentScheduleDatesBinding
    private lateinit var spTurns: Spinner

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

        spTurns = view.findViewById(R.id.sp_turn)
        val turnAdapter = ArrayAdapter.createFromResource(context!!, R.array.turn_list, R.layout.item_simple_spinner)
            .also {
                it.setDropDownViewResource(R.layout.item_simple_spinner)
            }
        spTurns.adapter = turnAdapter

        spTurns.onItemSelected { position ->
            val itemSelected = model.selectedAppointmentDate.get()
            itemSelected?.let {
                model.appointmentAdapter.items[it].turn = position
                model.appointmentAdapter.notifyDataSetChanged()
            }
        }

//        binding.setVariable(BR.viewModel, model)


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
