package com.peruapps.icnclient.features.register.presentation.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.peruapps.icnclient.R
import kotlinx.android.synthetic.main.custom_dialog_contact_us.*

class DialogRegister (context: Context, style: Int) : Dialog(context, style) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(R.layout.dialog_account_type)
        initEvents()
    }

    private fun initEvents() {
        btnCLoseContactUs.setOnClickListener { this.dismiss() }
    }
}