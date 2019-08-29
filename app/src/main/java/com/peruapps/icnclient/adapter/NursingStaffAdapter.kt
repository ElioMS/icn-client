package com.peruapps.icnclient.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.peruapps.icnclient.databinding.RvItemStaffServicesBinding
import com.peruapps.icnclient.model.ServiceType
import com.peruapps.icnclient.ui.holder.NursingStaffViewHolder

class NursingStaffAdapter(var items: MutableList<ServiceType>,
                          var listener: (ServiceType, Int) -> Unit) : RecyclerView.Adapter<NursingStaffViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NursingStaffViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val binding = RvItemStaffServicesBinding.inflate(layoutInflater, parent, false)
        return NursingStaffViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: NursingStaffViewHolder, position: Int) {
        val model = items[position]
        holder.bind(model)
        holder.layout.setOnClickListener {
            listener.invoke(model, position)
        }
    }

    fun bindItems(data: MutableList<ServiceType>) {
        this.items = data
        this.notifyDataSetChanged()
    }

}