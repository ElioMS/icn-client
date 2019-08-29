package com.peruapps.icnclient.ui.holder

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.peruapps.icnclient.R
import com.peruapps.icnclient.databinding.RvItemStaffServicesBinding
import com.peruapps.icnclient.model.ServiceType

class NursingStaffViewHolder(private var binding: RvItemStaffServicesBinding): RecyclerView.ViewHolder(binding.root) {

    var layout: ConstraintLayout = itemView.findViewById(R.id.rv_main_container)

    fun bind(data: ServiceType) {
        binding.model = data
        binding.executePendingBindings()
    }
}