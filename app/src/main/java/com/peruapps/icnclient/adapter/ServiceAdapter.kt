package com.peruapps.icnclient.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.peruapps.icnclient.databinding.RvItemServiceBinding
import com.peruapps.icnclient.model.request.Service
import com.peruapps.icnclient.ui.holder.ServiceViewHolder

class ServiceAdapter (private var items: ArrayList<Service>,
                      private var listener: OnItemClickListener):
    RecyclerView.Adapter<ServiceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val binding = RvItemServiceBinding.inflate(layoutInflater, parent, false)
        return ServiceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int)
            = holder.bind(items[position])

    override fun getItemCount(): Int = items.size

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun replaceData(arrayList: ArrayList<Service>) {
        items.addAll(arrayList)
        notifyDataSetChanged()
    }

}