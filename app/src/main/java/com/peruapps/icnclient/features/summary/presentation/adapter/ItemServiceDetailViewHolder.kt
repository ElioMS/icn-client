package com.peruapps.icnclient.features.summary.presentation.adapter

import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.peruapps.icnclient.R
import com.peruapps.icnclient.databinding.RvItemSummaryServiceDetailBinding
import com.peruapps.icnclient.model.SummaryServiceDetail
import com.peruapps.icnclient.room.entity.ServiceDetail

class ItemServiceDetailViewHolder(private var binding: RvItemSummaryServiceDetailBinding) :
    RecyclerView.ViewHolder(binding.root) {

    var expand: AppCompatImageView = itemView.findViewById(R.id.expandData)
    var shrink: AppCompatImageView = itemView.findViewById(R.id.shrinkData)
//    var data: RecyclerView = itemView.findViewById(R.id.rvSubstances)

    fun bind(data: ServiceDetail) {
        binding.item = data
        binding.executePendingBindings()
    }
}