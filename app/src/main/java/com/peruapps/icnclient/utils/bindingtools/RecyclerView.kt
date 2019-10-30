package com.peruapps.icnclient.utils.bindingtools

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.peruapps.icnclient.adapter.*
import com.peruapps.icnclient.features.notifications.presentation.adapter.ItemNotificationAdapter
import com.peruapps.icnclient.features.substances.presentation.adapter.ItemSubstanceAdapter
import com.peruapps.icnclient.features.substances.presentation.adapter.ItemSubstanceDetailAdapter
import com.peruapps.icnclient.features.summary.presentation.adapter.ItemCreditCardAdapter
import com.peruapps.icnclient.model.AppointmentDate

@BindingAdapter("appointmentsAdapter")
fun <T> setAppointmentsAdapter(recyclerView: RecyclerView, masterAdapter: AppointmentAdapter) {
    recyclerView.layoutManager = LinearLayoutManager(recyclerView.context, LinearLayoutManager.HORIZONTAL, false)
    recyclerView.adapter = masterAdapter
}

@BindingAdapter("itemReservationAdapter")
fun <T> setItemReservationAdapter(recyclerView: RecyclerView, masterAdapter: ReservationAdapter) {
    recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
    recyclerView.adapter = masterAdapter
}

@BindingAdapter("itemServiceAdapter")
fun <T> setServiceAdapter(recyclerView: RecyclerView, masterAdapter: ServiceAdapter) {
    recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
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

@BindingAdapter("itemSubstanceAdapter")
fun <T> setItemSubstanceAdapter(recyclerView: RecyclerView, masterAdapter: ItemSubstanceAdapter) {
    recyclerView.layoutManager = LinearLayoutManager(recyclerView.context, LinearLayoutManager.HORIZONTAL, false)
    recyclerView.adapter = masterAdapter
}

@BindingAdapter("itemDetailSubstanceAdapter")
fun <T> setItemDetailSubstanceAdapter(recyclerView: RecyclerView, masterAdapter: ItemSubstanceDetailAdapter) {
    recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
    recyclerView.adapter = masterAdapter
}

@BindingAdapter("itemCreditCardAdapter")
fun <T> setItemCreditCardAdapter(recyclerView: RecyclerView, masterAdapter: ItemCreditCardAdapter) {
    recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
    recyclerView.adapter = masterAdapter
}

@BindingAdapter("itemNotificationAdapter")
fun <T> setItemNotificationAdapter(recyclerView: RecyclerView, masterAdapter: ItemNotificationAdapter) {
    recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
    recyclerView.adapter = masterAdapter
}

@BindingAdapter("turnAdapter")
fun <T> setTurnAdapter(textView: TextView, position: AppointmentDate) {
    val data = when (position.turn) {
        0 -> "Día"
        1 -> "Noche"
        2 -> "24 hrs"
        else -> position.stringHour
    }
    textView.text = data
}