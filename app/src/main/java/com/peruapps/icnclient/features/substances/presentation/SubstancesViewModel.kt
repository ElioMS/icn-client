package com.peruapps.icnclient.features.substances.presentation

import android.util.Log
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import com.peruapps.icnclient.features.substances.data.SubstanceRepository
import com.peruapps.icnclient.features.substances.presentation.adapter.ItemSubstanceAdapter
import com.peruapps.icnclient.features.substances.presentation.adapter.ItemSubstanceDetailAdapter
import com.peruapps.icnclient.model.Substance
import com.peruapps.icnclient.model.SubstanceDetail
import com.peruapps.icnclient.ui.base.BaseViewModel

class SubstancesViewModel(private val repository: SubstanceRepository) : BaseViewModel<SubstancesNavigator>() {

//    val adapter = ItemSubstanceAdapter(arrayListOf()) {
//        model, position -> onSelectedSubstance(model, position)
//    }

    val detailAdapter = ItemSubstanceDetailAdapter(arrayListOf()) {
        model, position -> onSelectedItem(model, position)
    }

    val substanceQuantity = ObservableInt(0)
    val currentPosition = ObservableField<Int>()
    val substanceItemList = MutableLiveData<ArrayList<SubstanceDetail>>()

    init {
//        loadSubstances()
//        addSubstanceItems()
    }

    private fun onSelectedItem(model: SubstanceDetail, position: Int) {
        Log.d("item", position.toString())
        currentPosition.set(position)
        getNavigator().openSubstanceDialog()
    }

    private fun onSelectedSubstance(model: Substance, position: Int)  {
        Log.d("sustancia", position.toString())
    }

//    private fun loadSubstances() {
//        startJob {
//            val response = repository.listSubstances()
//            adapter.bindItems(ArrayList(response))
//        }
//    }

    fun addSubstanceItems() {
        val arrayList = ArrayList<SubstanceDetail>()
        for (i in 1..substanceQuantity.get()) {
            arrayList.add(SubstanceDetail())
        }

        substanceItemList.value = arrayList
        detailAdapter.bindItems(arrayList)
    }

}