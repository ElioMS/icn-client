package com.peruapps.icnclient.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.peruapps.icnclient.R
import com.peruapps.icnclient.databinding.RvItemServiceTypeBinding
import com.peruapps.icnclient.model.ServiceType
import com.peruapps.icnclient.ui.holder.ServiceTypeViewHolder

class ItemServiceTypeAdapter (var items: MutableList<ServiceType>,
                              var listener: (ServiceType, Int) -> Unit) : RecyclerView.Adapter<ServiceTypeViewHolder>()
{
    private var lastCheckedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceTypeViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val binding = RvItemServiceTypeBinding.inflate(layoutInflater, parent, false)
        return ServiceTypeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ServiceTypeViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        val model = items[position]
        holder.bind(model)

        val btn = holder.btnItem

        if (position == lastCheckedPosition) {
            verifyState(btn)
        }

        btn.setOnClickListener {
            lastCheckedPosition = position
            verifyState(btn)
            notifyDataSetChanged()
            listener.invoke(model, position)
        }
    }

    fun bindItems(data: MutableList<ServiceType>) {
        this.items = data
        this.notifyDataSetChanged()
    }

    private fun verifyState(btn: Button) {
        val colorSelected = ColorStateList.valueOf( ContextCompat.getColor(btn.context, R.color.colorPrimary))
        btn.background.setTintList(colorSelected)
        btn.setTextColor(Color.WHITE)
        btn.setTypeface(btn.typeface, Typeface.BOLD)
    }

    fun cleanList(){
        lastCheckedPosition = -1
        notifyDataSetChanged()
    }

}