package com.peruapps.icnclient.features.substances.presentation

import android.app.DatePickerDialog
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
import com.peruapps.icnclient.databinding.FragmentSubstanceBinding
import com.peruapps.icnclient.features.substances.presentation.dialogs.SubstanceDialog
import com.peruapps.icnclient.features.summary.presentation.SummaryActivity
import com.peruapps.icnclient.helpers.NavigationHelper
import com.peruapps.icnclient.model.Service
import com.peruapps.icnclient.model.ServiceType
import com.peruapps.icnclient.model.SubstanceDetail
import kotlinx.android.synthetic.main.fragment_service_type.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SubstancesFragment : Fragment(), SubstancesNavigator {

    private lateinit var binding: FragmentSubstanceBinding
    val model: SubstancesViewModel by viewModel()

    private lateinit var spSchedule: Spinner

    var categoryId: Int = 0
    private lateinit var service: Service
    private lateinit var serviceType: ServiceType

    companion object {
        fun setData(categoryId: Int, service: Service, serviceType: ServiceType) = SubstancesFragment().apply {
            this.categoryId = categoryId
            this.service = service
            this.serviceType = serviceType
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_substance, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setVariable(BR.viewModel, model)
        model.setNavigator(this)

        model.service.set(service)
        model.serviceType.set(serviceType)

        spSchedule = view.findViewById(R.id.spPeriod)
        val monthAdapter = ArrayAdapter.createFromResource(context!!, R.array.period_list, R.layout.item_simple_spinner)
            .also {
                it.setDropDownViewResource(R.layout.item_simple_spinner)
            }
        spSchedule.adapter = monthAdapter
//        model.substanceQuantity.set(quantity)
//
//        model.addSubstanceItems()
    }

    override fun updateDetail(data: SubstanceDetail) {
//        val position = model.currentPosition.get()
//
//        position?.let {
//            Log.d("dialog_data", it.toString())
//            model.detailAdapter.items[it] = data
//            model.detailAdapter.notifyDataSetChanged()
//        }
    }

    override fun showTimePicker() {
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
            model.hour.set(selectedHour)

            Toast.makeText(context!!, selectedHour, Toast.LENGTH_LONG).show()

        }),11, 12,false)
        tpDialog.show()
    }

    override fun showDatePicker() {

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val dpDialog = DatePickerDialog(context!!, datePickerListener, year, month, day)
        dpDialog.show()
    }

    private val datePickerListener =
        DatePickerDialog.OnDateSetListener { _, selectedYear, selectedMonth, selectedDay ->
            val currentMonth = selectedMonth + 1

            val calendar = Calendar.getInstance()
            calendar.set(Calendar.YEAR, selectedYear)
            calendar.set(Calendar.MONTH, selectedMonth)
            calendar.set(Calendar.DAY_OF_MONTH, selectedDay)

            val month = if (currentMonth < 10) "0"+currentMonth else currentMonth
            val day = if (selectedDay < 10) "0"+selectedDay else selectedDay

            val date = calendar.time
            val spanish = Locale("es", "ES")
            val formatDate = SimpleDateFormat("yyy-MM-dd", spanish).format(date)
            val weekdayName = SimpleDateFormat("EEEE dd", spanish).format(date)

            model.dateToString.set(weekdayName.capitalize())
            model.isoDate.set(formatDate)

            Log.d("picker_dialog", weekdayName.capitalize())
        }

    override fun showSummaryView() {
        NavigationHelper.redirectTo(activity!!, SummaryActivity::class.java)
//        NavigationHelper.changeFragment(fragmentManager!!,
//            R.id.main_container, SummaryFragment.newInstance(service, serviceType, ArrayList(model.detailAdapter.items)), "SummaryFragment")
    }
}
