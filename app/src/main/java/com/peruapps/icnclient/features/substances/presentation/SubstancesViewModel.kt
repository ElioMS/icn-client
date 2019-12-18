package com.peruapps.icnclient.features.substances.presentation

import android.util.Log
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import com.peruapps.icnclient.features.substances.data.SubstanceRepository
import com.peruapps.icnclient.features.substances.presentation.adapter.ItemSubstanceAdapter
import com.peruapps.icnclient.features.substances.presentation.adapter.ItemSubstanceDetailAdapter
import com.peruapps.icnclient.model.Service
import com.peruapps.icnclient.model.ServiceType
import com.peruapps.icnclient.model.Substance
import com.peruapps.icnclient.model.SubstanceDetail
import com.peruapps.icnclient.room.entity.ServiceDetail
import com.peruapps.icnclient.room.repository.ServiceDetailRepository
import com.peruapps.icnclient.ui.base.BaseViewModel

class SubstancesViewModel(private val repository: SubstanceRepository,
                          private val serviceDetailRepository: ServiceDetailRepository
) : BaseViewModel<SubstancesNavigator>() {

    val detailAdapter = ItemSubstanceDetailAdapter(arrayListOf())

    val adapter = ItemSubstanceAdapter(arrayListOf()) { model, position ->
        onSelectedSubstance(model, position)
    }

    var body = ObservableField<SubstanceDetail>()
    var selectedSubstance = ObservableField<Substance>()

    var itemCount = ObservableField(0)

    var isoDate = ObservableField<String>("")
    var dateToString = ObservableField<String>("")
    var hour = ObservableField<String>("")
    var period = ObservableInt(0)
    var days = ObservableField<String>("")

    val service = ObservableField<Service>()
    val serviceType = ObservableField<ServiceType>()

    init {
        loadSubstances()
//        body.set(SubstanceDetail())
    }

    private fun onSelectedSubstance(model: Substance, position: Int) {
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

    fun addSubstance() {
        val data = SubstanceDetail(
            selectedSubstance.get(),
            days.get()!!.toInt(),
            period.get(),
            isoDate.get(),
            dateToString.get(),
            hour.get()
        )

        detailAdapter.bindItems(data)
        itemCount.set(detailAdapter.itemCount)

//        detailAdapter.notifyDataSetChanged()
    }

    fun onClickGenerateButton() {
        startJob {
            serviceDetailRepository.insert(
                ServiceDetail(
                    serviceId = service.get()!!.id,
                    serviceName = service.get()!!.name,
                    serviceTypeId = serviceType.get()!!.id,
                    serviceTypeName = serviceType.get()!!.name,
                    price = serviceType.get()!!.price!!
                )
            )
        }
        getNavigator().showSummaryView()
    }

}