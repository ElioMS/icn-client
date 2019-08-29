package com.peruapps.icnclient.ui.holder

import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.peruapps.icnclient.R
import com.peruapps.icnclient.databinding.RvItemServiceTypeBinding
import com.peruapps.icnclient.model.ServiceType


class ServiceTypeViewHolder (private var binding: RvItemServiceTypeBinding) : RecyclerView.ViewHolder(binding.root) {

    var btnItem: Button = itemView.findViewById(R.id.btnServiceItem)

    fun bind(data: ServiceType) {
        binding.item = data
        binding.executePendingBindings()
    }

}