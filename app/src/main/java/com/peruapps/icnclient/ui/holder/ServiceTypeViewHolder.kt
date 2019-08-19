package com.peruapps.icnclient.ui.holder

import androidx.recyclerview.widget.RecyclerView
import com.peruapps.icnclient.databinding.RvItemServiceTypeBinding
import com.peruapps.icnclient.model.ServiceType


class ServiceTypeViewHolder (private var binding: RvItemServiceTypeBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: ServiceType) {
        binding.item = data
        binding.executePendingBindings()
    }

}