package com.peruapps.icnclient.features.substances.presentation.dialogs

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.peruapps.icnclient.BR
import com.peruapps.icnclient.R
import com.peruapps.icnclient.databinding.DialogSubstanceDetailBinding
import com.peruapps.icnclient.features.substances.presentation.SubstancesNavigator
import com.peruapps.icnclient.model.SubstanceDetail
import kotlinx.android.synthetic.main.fragment_service_type.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import androidx.databinding.adapters.TextViewBindingAdapter.setText
import android.widget.DatePicker
import android.util.Log
import com.peruapps.icnclient.extensions.toDate
import java.text.SimpleDateFormat


class SubstanceDialog : DialogFragment(), SubstanceDialogNavigator {


    private lateinit var binding: DialogSubstanceDetailBinding
    val model: SubstanceDialogViewModel by viewModel()

    private lateinit var navigator: SubstancesNavigator
    private lateinit var spSchedule: Spinner

    companion object {
        fun setData(navigator: SubstancesNavigator) = SubstanceDialog().apply {
            this.navigator = navigator
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setStyle(STYLE_NORMAL, R.style.FullScreenDialogFragmentStyle)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_substance_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.setNavigator(this)
        binding.setVariable(BR.viewModel, model)

        spSchedule = view.findViewById(R.id.spPeriod)
        val monthAdapter = ArrayAdapter.createFromResource(context!!, R.array.period_list, R.layout.item_simple_spinner)
            .also {
                it.setDropDownViewResource(R.layout.item_simple_spinner)
            }
        spSchedule.adapter = monthAdapter
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

    override fun sendDetailData(data: SubstanceDetail) {
        navigator.updateDetail(data)
        this.dismiss()
    }
}