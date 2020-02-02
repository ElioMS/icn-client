package com.peruapps.icnclient.features.summary.presentation

import android.util.Log
import androidx.databinding.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.peruapps.icnclient.R
import com.peruapps.icnclient.adapter.SwipeToDeleteCallback
import com.peruapps.icnclient.features.summary.presentation.adapter.ItemCreditCardAdapter
import com.peruapps.icnclient.features.summary.presentation.adapter.ItemServiceDetailAdapter
import com.peruapps.icnclient.model.*
import com.peruapps.icnclient.room.repository.ServiceDetailRepository
import com.peruapps.icnclient.ui.base.BaseViewModel

class SummaryViewModel(private val serviceDetailRepository: ServiceDetailRepository) :
    BaseViewModel<SummaryNavigator>() {

    val TAG = SummaryViewModel::class.java.simpleName

    val itemCreditCardAdapter = ItemCreditCardAdapter(arrayListOf()) { model, position ->
        selectedCreditCard(model, position)
    }

    val summaryPrice = ObservableFloat(0F)

    val service = ObservableField<Service>()
    val serviceType = ObservableField<ServiceType>()

    //    val substanceAdapter = ItemSubstanceDetailAdapter(arrayListOf())
    val servicesAdapter = ItemServiceDetailAdapter(arrayListOf()) { model, position ->
        detailView(model.id)
    }

    var receiptType = ObservableInt(1)
    var isVoucher = ObservableBoolean(true)
    val creditCard = ObservableField<CreditCard>()

    val clientAddress = ObservableField("")

    val address = ObservableField("")
    val documentNumber = ObservableField("")
    val businessName = ObservableField("")

    private val _validationMessage = MutableLiveData<Int>()
    val validationMessage: LiveData<Int>
        get() = _validationMessage

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
        creditCard.set(model)
    }

    private fun detailView(id: Int) = getNavigator().showDetail(id)

    fun selectReceiptType(isVoucherValue: Boolean, receiptTypeValue: Int) {
        isVoucher.set(isVoucherValue)
        receiptType.set(receiptTypeValue)
    }

    fun generateAppointment() {

        if (validation()) {
            startJob {
                serviceDetailRepository.deleteAll()
                getNavigator().showSuccessReservationDialog()
            }
        }
    }

    private fun validation(): Boolean {
        val creditCard = creditCard.get()
        val address = address.get()
        val documentNumber = documentNumber.get()
        val businessName = businessName.get()
        val clientAddress = clientAddress.get()

        if (clientAddress == "") {
            _validationMessage.value = R.string.validation_empty
            return false
        }

        if (creditCard == null) {
            _validationMessage.value = R.string.validation_credit_card
            return false
        }

        if ((receiptType.get() == 2 && address == "") || (receiptType.get() == 2 && documentNumber == "") || (receiptType.get() == 2 && businessName == "")) {
            _validationMessage.value = R.string.validation_empty
            return false
        }

        if  (receiptType.get() == 2 && documentNumber!!.length != 11) {
            _validationMessage.value = R.string.validation_ruc
            return false
        }

        return true
    }

    private fun fakeCreditCards() {
        val creditCards = ArrayList<CreditCard>()
        creditCards.add(CreditCard("************4554"))
        creditCards.add(CreditCard("************4556"))

        itemCreditCardAdapter.bindItems(creditCards)
    }

    fun onClickAddNewService() {
        getNavigator().addNewService()
    }

    val swipeHandler = object : SwipeToDeleteCallback() {
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//            super.onSwiped(viewHolder, direction)
            val position = viewHolder.adapterPosition
            val item = servicesAdapter.items[position]

            startJob {
                serviceDetailRepository.deleteById(item.id)
                val items = serviceDetailRepository.countItems()

                if  (items == 0) {
                    summaryPrice.set(0f)
                } else {
                    val price = serviceDetailRepository.summaryPrice()
                    summaryPrice.set(price)
                }

                Log.d(TAG, "$items")

                servicesAdapter.items.removeAt(position)
                servicesAdapter.notifyDataSetChanged()
            }


        }
    }
}