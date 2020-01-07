package com.peruapps.icnclient.features.summary.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.peruapps.icnclient.databinding.RvItemSummaryServiceDetailBinding
import com.peruapps.icnclient.model.SummaryServiceDetail
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.DefaultItemAnimator
import com.peruapps.icnclient.features.substances.presentation.adapter.ItemSubstanceDetailAdapter
import com.peruapps.icnclient.room.entity.ServiceDetail

class ItemServiceDetailAdapter(var items: MutableList<ServiceDetail>,
                               var listener: (ServiceDetail, Int) -> Unit) :
    RecyclerView.Adapter<ItemServiceDetailViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemServiceDetailViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RvItemSummaryServiceDetailBinding.inflate(layoutInflater, parent, false)
        return ItemServiceDetailViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ItemServiceDetailViewHolder, position: Int) {
        val model = items[position]
        holder.bind(model)

        holder.expand.setOnClickListener {
            listener.invoke(model, position)
        }

//       model.data?.let {
//           val mLayoutManager = LinearLayoutManager(holder.data.context)
//           holder.data.layoutManager = mLayoutManager
//           holder.data.adapter = ItemSubstanceDetailAdapter(it)
//       }
//
//        holder.expand.setOnClickListener {
//            holder.data.visibility = View.GONE
//            it.visibility = View.GONE
//            holder.shrink.visibility = View.VISIBLE
//        }
//
//        holder.shrink.setOnClickListener {
//            holder.data.visibility = View.VISIBLE
//            it.visibility = View.GONE
//            holder.expand.visibility = View.VISIBLE
//        }
    }

    fun bindItems(data: MutableList<ServiceDetail>) {
        this.items = data
        this.notifyDataSetChanged()
    }
}