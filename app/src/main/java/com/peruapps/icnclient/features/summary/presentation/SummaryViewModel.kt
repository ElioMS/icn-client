package com.peruapps.icnclient.features.summary.presentation

import android.util.Log
import androidx.databinding.*
import androidx.lifecycle.MutableLiveData
import com.peruapps.icnclient.db.sharePreferences.PreferencesManager
import com.peruapps.icnclient.features.substances.presentation.adapter.ItemSubstanceDetailAdapter
import com.peruapps.icnclient.features.summary.presentation.adapter.ItemCreditCardAdapter
import com.peruapps.icnclient.features.summary.presentation.adapter.ItemServiceDetailAdapter
import com.peruapps.icnclient.model.*
import com.peruapps.icnclient.room.entity.ServiceDetail
import com.peruapps.icnclient.room.repository.ServiceDetailRepository
import com.peruapps.icnclient.ui.base.BaseViewModel

class SummaryViewModel (private val serviceDetailRepository: ServiceDetailRepository) : BaseViewModel<SummaryNavigator>() {

    val itemCreditCardAdapter = ItemCreditCardAdapter(arrayListOf()) {
        model, position -> selectedCreditCard(model, position)
    }

    val summaryPrice = ObservableFloat(0F)

    val service = ObservableField<Service>()
    val serviceType = ObservableField<ServiceType>()

    val showSubstance = ObservableBoolean(false)


    val substanceAdapter = ItemSubstanceDetailAdapter(arrayListOf())
    val servicesAdapter = ItemServiceDetailAdapter(arrayListOf())

    var receiptType = ObservableInt(1)
    var isVoucher = ObservableBoolean(true)

    var summaryData = ObservableField<MutableList<SummaryServiceDetail>>(arrayListOf())

    init {
        fakeCreditCards()

        startJob {
            val list = serviceDetailRepository.list()
            servicesAdapter.bindItems(ArrayList(list))

            val price = serviceDetailRepository.summaryPrice()
            summaryPrice.set(price)
        }
    }

    private fun selectedCreditCard(model: CreditCard, position: Int) {

    }

    fun selectReceiptType(isVoucherValue: Boolean, receiptTypeValue: Int) {
        isVoucher.set(isVoucherValue)
        receiptType.set(receiptTypeValue)
    }

    fun generateAppointment() {
        // ADD WEB SERVICE
//        preferencesManager.removeSummaryData()
        startJob {
            serviceDetailRepository.deleteAll()
        }

        getNavigator().showSuccessReservationDialog()
    }

    private fun fakeCreditCards() {
        val creditCards = ArrayList<CreditCard>()
        creditCards.add(CreditCard("************4554"))
        creditCards.add(CreditCard("************4556"))

        itemCreditCardAdapter.bindItems(creditCards)
    }

    fun onClickShowSubstance() {
        val value = !showSubstance.get()
        showSubstance.set(value)
    }

    fun onClickAddNewService() {
        getNavigator().addNewService()
    }
}