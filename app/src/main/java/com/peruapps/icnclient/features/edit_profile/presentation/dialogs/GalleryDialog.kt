package com.peruapps.icnclient.features.edit_profile.presentation.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.peruapps.icnclient.R
import com.peruapps.icnclient.features.edit_profile.presentation.EditProfileNavigator
import kotlinx.android.synthetic.main.dialog_gallery.*

class GalleryDialog (context: Context,
                     var openGallery: () -> Unit,
                     var openCamera: () -> Unit

): Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        setContentView(R.layout.dialog_gallery)

        btnOpenGallery.setOnClickListener {
            openGallery.invoke()
            dismiss()
        }

        btnOpenCamera.setOnClickListener {
            openCamera.invoke()
            dismiss()
        }
    }

}