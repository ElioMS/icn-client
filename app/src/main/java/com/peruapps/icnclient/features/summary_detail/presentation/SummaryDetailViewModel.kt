package com.peruapps.icnclient.features.summary_detail.presentation

import android.util.Log
import androidx.databinding.ObservableField
import com.peruapps.icnclient.features.substances.data.SubstanceRepository
import com.peruapps.icnclient.features.substances.presentation.adapter.ItemSubstanceAdapter
import com.peruapps.icnclient.features.substances.presentation.adapter.ItemSubstanceDetailAdapter
import com.peruapps.icnclient.features.summary_detail.presentation.adapter.ItemSummaryDetailAdapter
import com.peruapps.icnclient.model.Substance
import com.peruapps.icnclient.model.SubstanceDetail
import com.peruapps.icnclient.room.entity.ServiceDetail
import com.peruapps.icnclient.room.entity.SubstanceTable
import com.peruapps.icnclient.room.repository.ServiceDetailRepository
import com.peruapps.icnclient.ui.base.BaseViewModel

class SummaryDetailViewModel(
    private val serviceDetailRepository: ServiceDetailRepository,
    private val substanceRepository: SubstanceRepository
) :
    BaseViewModel<SummaryDetailNavigator>() {

    val TAG = SummaryDetailViewModel::class.java.simpleName

    val dataService = ObservableField<ServiceDetail>()
    val substances = ObservableField<ArrayList<SubstanceTable>>()

    val adapter = ItemSummaryDetailAdapter(arrayListOf())

    fun loadData(id: Int) {
        startJob {
            dataService.set(serviceDetailRepository.findById(id))

            val substancesArray = substanceRepository.getSubstancesByParentId(id)
            adapter.bindItems(ArrayList(substancesArray))
        }
    }

}