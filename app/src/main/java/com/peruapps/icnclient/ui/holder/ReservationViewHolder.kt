package com.peruapps.icnclient.ui.holder

import androidx.recyclerview.widget.RecyclerView
import com.peruapps.icnclient.databinding.RvItemAppointmentBinding
import com.peruapps.icnclient.model.Appointment

class ReservationViewHolder (private var binding: RvItemAppointmentBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(appointment: Appointment) {
        binding.appointment = appointment
        binding.executePendingBindings()
    }
}