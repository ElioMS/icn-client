package com.peruapps.icnclient.features.summary_detail.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.peruapps.icnclient.databinding.RvItemAppointmentDateBinding
import com.peruapps.icnclient.room.entity.PersonalTable

class ItemAppointmentDateAdapter(var items: MutableList<PersonalTable>) :
    RecyclerView.Adapter<ItemAppointmentDateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAppointmentDateViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RvItemAppointmentDateBinding.inflate(layoutInflater, parent, false)
        return ItemAppointmentDateViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ItemAppointmentDateViewHolder, position: Int) {
        val model = items[position]
        holder.bind(model)
    }

    fun bindItems(data: MutableList<PersonalTable>) {
        this.items = data
        this.notifyDataSetChanged()
    }

}