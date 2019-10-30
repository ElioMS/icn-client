package com.peruapps.icnclient.utils.bindingtools

import android.view.View
import android.widget.LinearLayout
import androidx.databinding.BindingAdapter

@BindingAdapter("hasMaterial", "checkServiceId", "checkItemPosition", "showSubstances")
fun <T> setScheduleVisibility(
    layout: LinearLayout,
    hasMaterial: Boolean,
    serviceId: Int,
    itemPosition: Int,
    showSubstances: Boolean
) {


    if (serviceId == 1 && itemPosition == 1 || serviceId == 1 && itemPosition == 2) {
        layout.visibility = View.GONE
    } else if (hasMaterial && !showSubstances) {
        layout.visibility = View.VISIBLE
    } else if (showSubstances) {
        layout.visibility = View.GONE
    } else if (!hasMaterial) {
        layout.visibility = View.GONE
    }

}