package com.peruapps.icnclient.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.peruapps.icnclient.databinding.RvItemServiceTypeBinding
import com.peruapps.icnclient.model.ServiceType
import com.peruapps.icnclient.ui.holder.ServiceTypeViewHolder

class ServiceTypeAdapter(private var items: ArrayList<ServiceType>): RecyclerView.Adapter<ServiceTypeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceTypeViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val binding = RvItemServiceTypeBinding.inflate(layoutInflater, parent, false)
        return ServiceTypeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ServiceTypeViewHolder, position: Int) {
        holder.bind(items[position])
    }

}