package com.peruapps.icnclient.features.notifications.presentation.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.peruapps.icnclient.R
import com.peruapps.icnclient.features.notifications.presentation.NotificationNavigator
import kotlinx.android.synthetic.main.dialog_notification.*

class NotificationDialog(context: Context, style: Int, private val navigator: NotificationNavigator) :
    Dialog(context, style) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        setContentView(R.layout.dialog_notification)
        initEvents()
    }

    private fun initEvents() {
        closeNotificationDialog.setOnClickListener { this.dismiss() }
        btnYes.setOnClickListener { navigator.confirmNotification(true) }
        btnNo.setOnClickListener { navigator.confirmNotification(false) }
    }

}