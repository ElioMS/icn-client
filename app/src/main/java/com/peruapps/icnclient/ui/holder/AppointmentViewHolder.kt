package com.peruapps.icnclient.ui.holder

import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.peruapps.icnclient.R
import com.peruapps.icnclient.databinding.RvItemDateBinding
import com.peruapps.icnclient.model.AppointmentDate

class AppointmentViewHolder(private var binding: RvItemDateBinding): RecyclerView.ViewHolder(binding.root) {

    var layout: LinearLayout = itemView.findViewById(R.id.layout_dates)
    var date: TextView = itemView.findViewById(R.id.schedule_date)
    var hour: TextView = itemView.findViewById(R.id.schedule_hour)

    fun bind(data: AppointmentDate) {
        binding.item = data
        binding.executePendingBindings()
    }

}