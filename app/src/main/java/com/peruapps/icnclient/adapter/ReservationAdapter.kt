package com.peruapps.icnclient.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.peruapps.icnclient.databinding.RvItemAppointmentBinding
import com.peruapps.icnclient.entity.Appointment
import com.peruapps.icnclient.ui.holder.ReservationViewHolder

class ReservationAdapter (private var items: ArrayList<Appointment>):
    RecyclerView.Adapter<ReservationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val binding = RvItemAppointmentBinding.inflate(layoutInflater, parent, false)
        return ReservationViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ReservationViewHolder, position: Int) {
        holder.bind(items[position])
    }

}