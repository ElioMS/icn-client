package com.peruapps.icnclient.utils.bindingtools

import android.util.Log
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.peruapps.icnclient.adapter.*
import com.peruapps.icnclient.features.notifications.presentation.adapter.ItemNotificationAdapter
import com.peruapps.icnclient.features.substances.presentation.adapter.ItemSubstanceAdapter
import com.peruapps.icnclient.features.substances.presentation.adapter.ItemSubstanceDetailAdapter
import com.peruapps.icnclient.features.summary.presentation.adapter.ItemCreditCardAdapter
import com.peruapps.icnclient.features.summary.presentation.adapter.ItemServiceDetailAdapter
import com.peruapps.icnclient.features.summary_detail.presentation.adapter.ItemSummaryDetailAdapter
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

    val swipeHandler = object : SwipeToDeleteCallback() {
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//            super.onSwiped(viewHolder, direction)
            val position = viewHolder.adapterPosition
            masterAdapter.items.removeAt(position)
            masterAdapter.notifyDataSetChanged()
            Log.d("SWIPE", "SWIPE")
        }
    }

    val itemTouchHelper = ItemTouchHelper(swipeHandler)
    itemTouchHelper.attachToRecyclerView(recyclerView)
}

@BindingAdapter("itemCreditCardAdapter")
fun <T> setItemCreditCardAdapter(recyclerView: RecyclerView, masterAdapter: ItemCreditCardAdapter) {
    recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
    recyclerView.adapter = masterAdapter
}

@BindingAdapter("itemServiceDetailAdapter")
fun <T> setItemServiceDetailAdapter(recyclerView: RecyclerView, masterAdapter: ItemServiceDetailAdapter) {
    recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
    recyclerView.adapter = masterAdapter
}

@BindingAdapter("itemNotificationAdapter")
fun <T> setItemNotificationAdapter(recyclerView: RecyclerView, masterAdapter: ItemNotificationAdapter) {
    recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
    recyclerView.adapter = masterAdapter
}

@BindingAdapter("itemSummaryDetailAdapter")
fun <T> setItemSummaryDetailAdapter(recyclerView: RecyclerView, masterAdapter: ItemSummaryDetailAdapter) {
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

@BindingAdapter("swipeHandler")
fun <T> setSwipeHandler(recyclerView: RecyclerView, swipeHandler: SwipeToDeleteCallback) {
    val itemTouchHelper = ItemTouchHelper(swipeHandler)
    itemTouchHelper.attachToRecyclerView(recyclerView)
}