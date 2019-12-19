package com.peruapps.icnclient.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import com.peruapps.icnclient.R
import kotlinx.android.synthetic.main.custom_dialog_contact_us.*
import kotlinx.android.synthetic.main.custom_dialog_other_service.*

class CustomDialogOtherService(context: Context, style: Int) : Dialog(context, style) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(R.layout.custom_dialog_other_service)

        initEvents()
    }


    override fun onStart() {
        super.onStart()

        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.MATCH_PARENT
        window!!.setLayout(width, height)


    }


    private fun initEvents() {
        btn_close_dialog.setOnClickListener { this.dismiss() }
    }
}