package com.peruapps.icnclient.adapter

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.peruapps.icnclient.R
import com.peruapps.icnclient.databinding.RvItemDateBinding
import com.peruapps.icnclient.model.AppointmentDate
import com.peruapps.icnclient.ui.holder.AppointmentViewHolder

class AppointmentAdapter(var items: MutableList<AppointmentDate>,
                         var listener: (AppointmentDate, Int) -> Unit): RecyclerView.Adapter<AppointmentViewHolder>() {

    private var lastCheckedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val binding = RvItemDateBinding.inflate(layoutInflater, parent, false)
        return AppointmentViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: AppointmentViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        val model = items[position]
        holder.bind(items[position])

        val layout = holder.layout
        val date = holder.date
        val hour = holder.hour

        if  (lastCheckedPosition == -1) {
            lastCheckedPosition = 0
            verifyState(layout, date, hour)
        }

        if (position == lastCheckedPosition) {
            verifyState(layout, date, hour)
        }

        holder.layout.setOnClickListener {
            lastCheckedPosition = position
            verifyState(layout, date, hour)
            notifyDataSetChanged()
            listener.invoke(model, position)
        }
    }

    private fun verifyState(layout: LinearLayout, date: TextView, hour: TextView) {
        val colorSelected = ContextCompat.getDrawable(layout.context, R.drawable.background_white_round_corners)
        val colorTextSelected = ContextCompat.getColor(date.context, R.color.colorPrimary)
        val typeface = ResourcesCompat.getFont(date.context, R.font.muli_bold)

        layout.background = colorSelected
        date.setTextColor(colorTextSelected)

        date.typeface = typeface
        hour.setTextColor(colorTextSelected)
    }

    fun bindItems(data: MutableList<AppointmentDate>) {
        this.items = data
        this.notifyDataSetChanged()
    }

}