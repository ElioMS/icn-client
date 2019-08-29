package com.peruapps.icnclient.utils.bindingtools

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.peruapps.icnclient.adapter.AppointmentAdapter
import com.peruapps.icnclient.adapter.ItemServiceTypeAdapter
import com.peruapps.icnclient.adapter.NursingStaffAdapter

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