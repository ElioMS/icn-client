package com.peruapps.icnclient.features.substances.presentation.adapter

import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.peruapps.icnclient.R
import com.peruapps.icnclient.databinding.RvItemSubstancesBinding
import com.peruapps.icnclient.model.Substance

class ItemSubstanceViewHolder(private var binding: RvItemSubstancesBinding): RecyclerView.ViewHolder(binding.root) {

    var layout: LinearLayout = itemView.findViewById(R.id.rvItemSubstanceLayout)
    var textView: TextView = itemView.findViewById(R.id.tvItemSubstance)

    fun bind(data: Substance) {
        binding.item = data
        binding.executePendingBindings()
    }

}