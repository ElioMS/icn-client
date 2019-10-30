package com.peruapps.icnclient.features.substances.presentation.adapter

import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.peruapps.icnclient.R
import com.peruapps.icnclient.databinding.RvItemSubstanceDetailBinding
import com.peruapps.icnclient.model.SubstanceDetail

class ItemSubstanceDetailViewHolder(private var binding: RvItemSubstanceDetailBinding): RecyclerView.ViewHolder(binding.root) {

    var layout: LinearLayout = itemView.findViewById(R.id.rvItemSubstanceLayout)

    fun bind(data: SubstanceDetail) {
        binding.item = data
        binding.executePendingBindings()
    }

}