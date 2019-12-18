package com.peruapps.icnclient.utils.bindingtools

import android.net.Uri
import android.view.View
import androidx.databinding.BindingAdapter
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.common.ResizeOptions
import com.facebook.imagepipeline.request.ImageRequestBuilder

@BindingAdapter("setImageControllerResize")
fun setImageControllerResize(photo: SimpleDraweeView, uri: String?){
    if (uri != null) {
        photo.visibility = View.VISIBLE
        val request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(uri))
//            .setResizeOptions(ResizeOptions(width, height))
            .build()
        photo.controller = Fresco.newDraweeControllerBuilder()
            .setOldController(photo.controller)
            .setImageRequest(request)
            .build()
    }
}