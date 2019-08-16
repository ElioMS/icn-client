package com.peruapps.icnclient.widgets

import android.content.Context
import android.content.Intent
import android.widget.ImageView
import com.peruapps.icnclient.features.service_category.views.ServiceCategoryActivity

class CustomBottomBar (val img: ImageView, val ctx: Context) {

    fun toServiceCategories(){
        img.setOnClickListener {
            val page = Intent(ctx, ServiceCategoryActivity::class.java)
            ctx.startActivity(page)
        }
    }
}