package com.peruapps.icnclient.features.summary.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.peruapps.icnclient.databinding.RvItemCreditCardBinding
import com.peruapps.icnclient.model.CreditCard

class ItemCreditCardAdapter(
    var items: MutableList<CreditCard>,
    var listener: (CreditCard, Int) -> Unit
) : RecyclerView.Adapter<ItemCreditCardViewHolder>() {

    private var lastCheckedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemCreditCardViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RvItemCreditCardBinding.inflate(layoutInflater, parent, false)
        return ItemCreditCardViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ItemCreditCardViewHolder, position: Int) {
        holder.setIsRecyclable(false)

        val model = items[position]
        holder.bind(model)

        val layout = holder.layout
        val image = holder.image

        if (position == lastCheckedPosition) {
            verifyState(image)
        }

        layout.setOnClickListener {
            lastCheckedPosition = position
            verifyState(image)
            notifyDataSetChanged()
            listener.invoke(model, position)
        }
    }

    fun bindItems(data: MutableList<CreditCard>) {
        this.items = data
        this.notifyDataSetChanged()
    }

    private fun verifyState(imageViewCompat: AppCompatImageView) {
        imageViewCompat.visibility = View.VISIBLE

    }
}