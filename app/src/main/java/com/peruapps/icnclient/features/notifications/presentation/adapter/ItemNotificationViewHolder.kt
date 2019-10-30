package com.peruapps.icnclient.features.notifications.presentation.adapter

import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.peruapps.icnclient.R
import com.peruapps.icnclient.databinding.RvItemNotificationBinding
import com.peruapps.icnclient.model.response.NotificationResponse

class ItemNotificationViewHolder(private var binding: RvItemNotificationBinding) :
    RecyclerView.ViewHolder(binding.root) {

    val layout: LinearLayout = itemView.findViewById(R.id.rvItemNotificationLayout)

    fun bind(data: NotificationResponse) {
        binding.item = data
        binding.executePendingBindings()
    }
}