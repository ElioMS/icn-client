package com.peruapps.icnclient.utils.bindingtools

import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import com.peruapps.icnclient.R

@BindingAdapter("categoryId", "currentCategoryValue")
fun <T> setOnTextViewSelected(textView: TextView, modelValue: Int, textValue: Int) {
    if (modelValue == textValue) {
        textView.setTextColor(ContextCompat.getColor(textView.context, R.color.colorAccent))
//        textView.typeface = ResourcesCompat.getFont(textView.context, R.font.muli_bold)
    } else {
        textView.setTextColor(ContextCompat.getColor(textView.context, R.color.colorLightGray))
//        textView.typeface = ResourcesCompat.getFont(textView.context, R.font.muli_extralight)
    }
}

@BindingAdapter("radioIsChecked", "radioName")
fun <T> setActiveIsChecked(radioButton: RadioButton, isChecked: Boolean, name: Int) {

    if (isChecked && name == 1) {
        radioButton.background = ContextCompat.getDrawable(radioButton.context, R.drawable.btn_state_right)
        radioButton.setTextColor(Color.WHITE)
    }

    if (!isChecked && name == 2) {
        radioButton.background = ContextCompat.getDrawable(radioButton.context, R.drawable.btn_state_right)
        radioButton.setTextColor(Color.WHITE)
    }

    if (!isChecked && name == 1) {
        radioButton.background = ContextCompat.getDrawable(radioButton.context, R.drawable.btn_state_left)
        radioButton.setTextColor(ContextCompat.getColor(radioButton.context, R.color.colorSecondaryText))
    }

    if (isChecked && name == 2) {
        radioButton.background = ContextCompat.getDrawable(radioButton.context, R.drawable.btn_state_left)
        radioButton.setTextColor(ContextCompat.getColor(radioButton.context, R.color.colorSecondaryText))
    }
}

@BindingAdapter("numberToString")
fun <T> setNumberToString(textView: TextView, number: Int) {
    if (number < 10 && number != 0) {
        val lower = "0$number"
        textView.text = lower
    } else {
        textView.text = number.toString()
    }
}

@BindingAdapter("modelValue")
fun <T> setTextWithPrefix(textView: TextView, modelValue: String? = null) {
    var text = "Sustancia: "

    modelValue.let {
        text = "Sustancia : $modelValue"
    }

    textView.text = text
}

@BindingAdapter("documentValue", "optionValue")
fun <T> setDocumentValue(radioButton: RadioButton, value: Int, option: Int) {

//    val dni = radioButton.findViewById<View>(R.id.optDni)
//    val pasaporte = radioButton.findViewById<RadioButton>(R.id.optPasaporte)
//
//    when (value) {
//        1 -> {
//            dni.background = ContextCompat.getDrawable(radioButton.context, R.drawable.bg_dni_selected)
//            dni.color
//        }
//        2 -> {
//            pasaporte.background = ContextCompat.getDrawable(radioButton.context, R.drawable.btn_state_right)
//            pasaporte.setTextColor(Color.WHITE)
//        }
//    }

//    if (value == option) {

//        radioButton.background = ContextCompat.getDrawable(radioButton.context, R.drawable.btn_state_right)
//        radioButton.setTextColor(Color.WHITE)
//    } else {
//        radioButton.background = ContextCompat.getDrawable(radioButton.context, R.drawable.btn_state_left)
//        radioButton.setTextColor(ContextCompat.getColor(radioButton.context, R.color.colorSecondaryText))
//    }
}