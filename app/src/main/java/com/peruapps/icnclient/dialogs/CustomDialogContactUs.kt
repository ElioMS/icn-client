package com.peruapps.icnclient.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.peruapps.icnclient.R
import kotlinx.android.synthetic.main.custom_dialog_contact_us.*

class CustomDialogContactUs(context: Context, style: Int) : Dialog(context, style) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(R.layout.custom_dialog_contact_us)
        initEvents()
    }

    private fun initEvents() {
        btnCLoseContactUs.setOnClickListener { this.dismiss() }
    }
}
