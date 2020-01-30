package com.peruapps.icnclient.features.summary_detail.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.peruapps.icnclient.databinding.RvItemAppointmentDateBinding
import com.peruapps.icnclient.room.entity.PersonalTable

class ItemAppointmentDateViewHolder(private var binding: RvItemAppointmentDateBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(data: PersonalTable) {
        binding.item = data
        binding.executePendingBindings()
    }
}