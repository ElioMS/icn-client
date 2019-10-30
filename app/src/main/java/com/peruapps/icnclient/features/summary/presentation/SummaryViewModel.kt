package com.peruapps.icnclient.features.summary.presentation

import androidx.databinding.Observable
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import com.peruapps.icnclient.features.summary.presentation.adapter.ItemCreditCardAdapter
import com.peruapps.icnclient.model.CreditCard
import com.peruapps.icnclient.ui.base.BaseViewModel

class SummaryViewModel : BaseViewModel<SummaryNavigator>() {

    val itemCreditCardAdapter = ItemCreditCardAdapter(arrayListOf()) {
        model, position -> selectedCreditCard(model, position)
    }

    var receiptType = ObservableInt(1)
    var isVoucher = ObservableBoolean(true)

    init {
        fakeCreditCards()
    }

    private fun selectedCreditCard(model: CreditCard, position: Int) {

    }

    fun selectReceiptType(isVoucherValue: Boolean, receiptTypeValue: Int) {
        isVoucher.set(isVoucherValue)
        receiptType.set(receiptTypeValue)
    }

    fun generateAppointment() {
        // ADD WEB SERVICE
        getNavigator().showSuccessReservationDialog()
    }

    private fun fakeCreditCards() {
        val creditCards = ArrayList<CreditCard>()
        creditCards.add(CreditCard("************4554"))
        creditCards.add(CreditCard("************4556"))

        itemCreditCardAdapter.bindItems(creditCards)
    }
}