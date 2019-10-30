package com.peruapps.icnclient.features.summary.presentation.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.peruapps.icnclient.R
import com.peruapps.icnclient.features.summary.presentation.SummaryNavigator
import kotlinx.android.synthetic.main.dialog_success_reservation.*

class DialogSuccessReservation(context: Context, style: Int, val navigator: SummaryNavigator) : Dialog(context, style) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_success_reservation)
        initEvents()
    }

    private fun initEvents() {
        closeSuccessReservationDialog.setOnClickListener {
            navigator.showAppointmentsView()
        }
    }

}