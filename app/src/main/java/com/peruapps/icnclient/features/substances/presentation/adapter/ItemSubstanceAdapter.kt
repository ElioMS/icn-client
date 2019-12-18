package com.peruapps.icnclient.features.substances.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.peruapps.icnclient.R
import com.peruapps.icnclient.databinding.RvItemSubstancesBinding
import com.peruapps.icnclient.model.Substance

class ItemSubstanceAdapter(
    var items: MutableList<Substance>,
    var listener: (Substance, Int) -> Unit
) : RecyclerView.Adapter<ItemSubstanceViewHolder>() {

    private var lastCheckedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemSubstanceViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RvItemSubstancesBinding.inflate(layoutInflater, parent, false)
        return ItemSubstanceViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ItemSubstanceViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        val model = items[position]
        holder.bind(model)

        val layout = holder.layout
        val textView = holder.textView

        if (position == lastCheckedPosition) {
            verifyState(layout, textView)
        }

        layout.setOnClickListener {
            lastCheckedPosition = position
            verifyState(layout, textView)
            notifyDataSetChanged()
            listener.invoke(model, position)
        }
    }

    private fun verifyState(layout: LinearLayout, textView: TextView) {
        val colorSelected = ContextCompat.getDrawable(layout.context, R.drawable.background_primary_round_corners)
        val colorTextSelected = ContextCompat.getColor(textView.context, R.color.colorWhite)
        val typeface = ResourcesCompat.getFont(textView.context, R.font.muli_bold)

        layout.background = colorSelected
        textView.setTextColor(colorTextSelected)
        textView.typeface = typeface
    }

    fun bindItems(data: MutableList<Substance>) {
        this.items = data
        this.notifyDataSetChanged()
    }

}