package com.peruapps.icnclient.features.substances.presentation.dialogs

import android.graphics.Color
import android.util.Log
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.peruapps.icnclient.features.substances.data.SubstanceRepository
import com.peruapps.icnclient.features.substances.presentation.SubstancesNavigator
import com.peruapps.icnclient.features.substances.presentation.adapter.ItemSubstanceAdapter
import com.peruapps.icnclient.features.substances.presentation.adapter.ItemSubstanceViewHolder
import com.peruapps.icnclient.model.Substance
import com.peruapps.icnclient.model.SubstanceDetail
import com.peruapps.icnclient.ui.base.BaseViewModel

class SubstanceDialogViewModel(private val repository: SubstanceRepository) : BaseViewModel<SubstanceDialogNavigator>() {

    val adapter = ItemSubstanceAdapter(arrayListOf()) {
            model, position -> onSelectedSubstance(model, position)
    }

    var body = ObservableField<SubstanceDetail>()
    var selectedSubstance = ObservableField<Substance>()

    var isoDate = ObservableField<String>("")
    var dateToString = ObservableField<String>("")
    var hour = ObservableField<String>("")
    var period = ObservableInt(0)
    var days = ObservableField<String>("")

    init {
        loadSubstances()
//        body.set(SubstanceDetail())
    }

    private fun onSelectedSubstance(model: Substance, position: Int)  {
//        Log.d("sustancia", position.toString())
        selectedSubstance.set(model)
//        holder.layout.setBackgroundColor(Color.RED)
    }

    private fun loadSubstances() {
        startJob {
            val response = repository.listSubstances()
            adapter.bindItems(ArrayList(response))
        }
    }

    fun setTime() {
        getNavigator().showTimePicker()
    }

    fun setDate() {
        getNavigator().showDatePicker()
    }

    fun updateSubstanceDetail() {
        val data = SubstanceDetail(selectedSubstance.get(), days.get()!!.toInt(), period.get(), isoDate.get(), dateToString.get(), hour.get())
        Log.d("updateSubstanceDetail", days.get()!!)
        getNavigator().sendDetailData(data)
    }

}