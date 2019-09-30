package com.peruapps.icnclient.utils.bindingtools

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.peruapps.icnclient.adapter.AppointmentAdapter
import com.peruapps.icnclient.adapter.ItemServiceTypeAdapter
import com.peruapps.icnclient.adapter.NursingStaffAdapter
import com.peruapps.icnclient.model.AppointmentDate

@BindingAdapter("appointmentsAdapter")
fun <T> setAppointmentsAdapter(recyclerView: RecyclerView, masterAdapter: AppointmentAdapter) {
    recyclerView.layoutManager = LinearLayoutManager(recyclerView.context, LinearLayoutManager.HORIZONTAL, false)
    recyclerView.adapter = masterAdapter
}

@BindingAdapter("serviceTypesAdapter")
fun <T> setServiceTypesAdapter(recyclerView: RecyclerView, masterAdapter: ItemServiceTypeAdapter) {
    recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
    recyclerView.adapter = masterAdapter
}

@BindingAdapter("nursingStaffAdapter")
fun <T> setNursingStaffAdapter(recyclerView: RecyclerView, masterAdapter: NursingStaffAdapter) {
    recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
    recyclerView.adapter = masterAdapter
}

@BindingAdapter("turnAdapter")
fun <T> setTurnAdapter(textView: TextView, position: AppointmentDate) {
    val data = when (position.turn) {
        0 -> "Día"
        1 -> "Noche"
        2 -> "24 hrs"
        else -> position.hour
    }
    textView.text = data
}