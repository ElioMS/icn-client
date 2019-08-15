package com.peruapps.icnclient.ui.holder

import androidx.recyclerview.widget.RecyclerView
import com.peruapps.icnclient.databinding.RvItemServiceBinding
import com.peruapps.icnclient.model.request.Service

class ServiceViewHolder (private var binding: RvItemServiceBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(repo: Service) {
        binding.service = repo
//        if (listener != null) {
//            binding.root.setOnClickListener({ _ -> listener.onItemClick(layoutPosition) })
//        }

        binding.executePendingBindings()
    }
}