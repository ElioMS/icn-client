package com.peruapps.icnclient.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.peruapps.icnclient.databinding.RvItemServiceBinding
import com.peruapps.icnclient.model.Service
import com.peruapps.icnclient.ui.holder.ServiceViewHolder

class ServiceAdapter (private var items: MutableList<Service>,
                      var listener: (Service, Int) -> Unit):
    RecyclerView.Adapter<ServiceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RvItemServiceBinding.inflate(layoutInflater, parent, false)
        return ServiceViewHolder(binding)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int){
        holder.bind(items[position])

        holder.btnItemService.setOnClickListener {
            listener.invoke(items[position], position)
        }
    }

    override fun getItemCount(): Int = items.size

    fun bindItems(data: MutableList<Service>) {
        this.items = data
        this.notifyDataSetChanged()
    }
}