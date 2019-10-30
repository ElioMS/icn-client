package com.peruapps.icnclient.features.summary.presentation.adapter

import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.peruapps.icnclient.R
import com.peruapps.icnclient.databinding.RvItemCreditCardBinding
import com.peruapps.icnclient.model.CreditCard

class ItemCreditCardViewHolder (private var binding: RvItemCreditCardBinding): RecyclerView.ViewHolder(binding.root) {

    var layout: LinearLayout = itemView.findViewById(R.id.layoutItemCreditCard)
    var image: AppCompatImageView = itemView.findViewById(R.id.ivSelected)

    fun bind(data: CreditCard) {
        binding.item = data
        binding.executePendingBindings()
    }

}