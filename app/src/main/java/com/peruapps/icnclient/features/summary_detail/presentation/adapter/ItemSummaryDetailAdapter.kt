package com.peruapps.icnclient.features.summary_detail.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.peruapps.icnclient.databinding.RvItemSummaryDetailBinding
import com.peruapps.icnclient.features.summary.presentation.adapter.ItemCreditCardViewHolder
import com.peruapps.icnclient.room.entity.SubstanceTable

class ItemSummaryDetailAdapter(var items: MutableList<SubstanceTable>) :
    RecyclerView.Adapter<ItemSummaryDetailViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemSummaryDetailViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RvItemSummaryDetailBinding.inflate(layoutInflater, parent, false)
        return ItemSummaryDetailViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ItemSummaryDetailViewHolder, position: Int) {
        val model = items[position]
        holder.bind(model)
    }

    fun bindItems(data: MutableList<SubstanceTable>) {
        this.items = data
        this.notifyDataSetChanged()
    }
}