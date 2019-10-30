package com.peruapps.icnclient.features.notifications.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.peruapps.icnclient.databinding.RvItemNotificationBinding
import com.peruapps.icnclient.model.response.NotificationResponse

class ItemNotificationAdapter(
    var items: MutableList<NotificationResponse>,
    var listener: (NotificationResponse, Int) -> Unit
) : RecyclerView.Adapter<ItemNotificationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemNotificationViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RvItemNotificationBinding.inflate(layoutInflater, parent, false)
        return ItemNotificationViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ItemNotificationViewHolder, position: Int) {
        val model = items[position]
        holder.bind(model)

        holder.layout.setOnClickListener {
            listener.invoke(model, position)
        }
    }

    fun bindItems(data: MutableList<NotificationResponse>) {
        this.items = data
        this.notifyDataSetChanged()
    }

}