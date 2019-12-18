package com.peruapps.icnclient.features.substances.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.peruapps.icnclient.databinding.RvItemSubstanceDetailBinding
import com.peruapps.icnclient.model.SubstanceDetail

class ItemSubstanceDetailAdapter(var items: MutableList<SubstanceDetail>): RecyclerView.Adapter<ItemSubstanceDetailViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemSubstanceDetailViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RvItemSubstanceDetailBinding.inflate(layoutInflater, parent, false)
        return ItemSubstanceDetailViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ItemSubstanceDetailViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        val model = items[position]
        holder.bind(model)

        val layout = holder.layout

        layout.setOnClickListener {
//            listener.invoke(model, position)
        }
    }

    fun bindItems(data: SubstanceDetail) {
        this.items.add(data)
        this.notifyDataSetChanged()
    }

}