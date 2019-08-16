package com.peruapps.icnclient.ui.holder

import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.peruapps.icnclient.R
import com.peruapps.icnclient.databinding.RvItemServiceBinding
import com.peruapps.icnclient.model.Service

class ServiceViewHolder (private var binding: RvItemServiceBinding): RecyclerView.ViewHolder(binding.root) {
    var btnItemService: Button = itemView.findViewById(R.id.btnServiceItem)

    fun bind(repo: Service) {
        binding.service = repo
//        if (listener != null) {
//            binding.root.setOnClickListener({ _ -> listener.onItemClick(layoutPosition) })
//        }

        binding.executePendingBindings()
    }
}