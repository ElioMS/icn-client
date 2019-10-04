package com.peruapps.icnclient.features.account.presentation.views.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.peruapps.icnclient.R
import com.peruapps.icnclient.features.account.presentation.views.ForgotPasswordNavigator
import com.peruapps.icnclient.helpers.NavigationHelper
import kotlinx.android.synthetic.main.custom_dialog_code.*
import kotlinx.android.synthetic.main.custom_dialog_contact_us.*

class CodeDialog(context: Context, private val navigator: ForgotPasswordNavigator) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(R.layout.custom_dialog_code)
        initEvents()
    }

    private fun initEvents() {
        btnSend.setOnClickListener {
            this.dismiss()
            navigator.showResetPasswordView()
        }
    }
}
