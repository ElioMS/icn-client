package com.peruapps.icnclient.features.summary_detail.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.peruapps.icnclient.databinding.RvItemSummaryDetailBinding
import com.peruapps.icnclient.room.entity.SubstanceTable

class ItemSummaryDetailViewHolder(private var binding: RvItemSummaryDetailBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(data: SubstanceTable) {
        binding.item = data
        binding.executePendingBindings()
    }

}