package com.peruapps.icnclient.features.substances.presentation

import android.util.Log
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.peruapps.icnclient.R
import com.peruapps.icnclient.features.substances.data.SubstanceRepository
import com.peruapps.icnclient.features.substances.presentation.adapter.ItemSubstanceAdapter
import com.peruapps.icnclient.features.substances.presentation.adapter.ItemSubstanceDetailAdapter
import com.peruapps.icnclient.model.Service
import com.peruapps.icnclient.model.ServiceType
import com.peruapps.icnclient.model.Substance
import com.peruapps.icnclient.model.SubstanceDetail
import com.peruapps.icnclient.room.entity.ServiceDetail
import com.peruapps.icnclient.room.entity.SubstanceTable
import com.peruapps.icnclient.room.repository.ServiceDetailRepository
import com.peruapps.icnclient.ui.base.BaseViewModel

class SubstancesViewModel(
    private val repository: SubstanceRepository,
    private val serviceDetailRepository: ServiceDetailRepository
) : BaseViewModel<SubstancesNavigator>() {

    val detailAdapter = ItemSubstanceDetailAdapter(arrayListOf())

    val adapter = ItemSubstanceAdapter(arrayListOf()) { model, position ->
        onSelectedSubstance(model, position)
    }

    var selectedSubstance = ObservableField<Substance>()

    var itemCount = ObservableField(0)

    var isoDate = ObservableField<String>("")
    var dateToString = ObservableField<String>("")
    var hour = ObservableField<String>("")
    var period = ObservableInt(0)
    var days = ObservableField<String>("")

    val service = ObservableField<Service>()
    val serviceType = ObservableField<ServiceType>()

    private val _validationMessage = MutableLiveData<Int>()
    val validationMessage: LiveData<Int>
        get() = _validationMessage

    init {
        loadSubstances()
    }

    private fun onSelectedSubstance(model: Substance, position: Int) {
        selectedSubstance.set(model)
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

        if (addSubstanceValidation()) {
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
        }
    }

    fun onClickGenerateButton() {

        val countItems = detailAdapter.items.size

        if (countItems > 0) {
            startJob {
                val item = serviceDetailRepository.insert(
                    ServiceDetail(
                        serviceId = service.get()!!.id,
                        serviceName = service.get()!!.name,
                        serviceTypeId = serviceType.get()!!.id,
                        serviceTypeName = serviceType.get()!!.name,
                        price = serviceType.get()!!.price!!
                    )
                )

                detailAdapter.items.forEach { x ->
                    repository.storeSubstances(
                        SubstanceTable(
                            substanceId = x.substance!!.id.toInt(),
                            substanceName = x.substance!!.name,
                            period = x.timePeriod!!,
                            days = x.daysQuantity!!,
                            date = x.dateToString!!,
                            hour = x.hour!!,
                            detailId = item.toInt()
                        )
                    )
                }
            }
            getNavigator().showSummaryView()
        } else {
            _validationMessage.value = R.string.validation_empty
        }
    }


    private fun addSubstanceValidation(): Boolean {

        val substance = selectedSubstance.get()
        val days = days.get()
        val period = period.get()
        val date = isoDate.get()
        val hour = hour.get()

        if (substance == null) {
            _validationMessage.value = R.string.validation_empty
            return false
        }

        if (days == "") {
            _validationMessage.value = R.string.validation_empty
            return false
        }

        if (date == "") {
            _validationMessage.value = R.string.validation_empty
            return false
        }

        if (hour == "") {
            _validationMessage.value = R.string.validation_empty
            return false
        }

        return true
    }
}