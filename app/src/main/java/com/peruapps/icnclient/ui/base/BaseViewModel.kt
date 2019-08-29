package com.peruapps.icnclient.ui.base

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.peruapps.icnclient.dialogs.CustomDialogContactUs
import com.peruapps.icnclient.features.services.views.ServicesNavigator


abstract class BaseViewModel <T> : ViewModel() {

    private var navigator: T? = null

    var hasMaterial = ObservableField<Boolean>()
    var doSchedule = ObservableField<Boolean>()
    var selectScheduleType = ObservableField<Boolean>()

    fun getNavigator() : T {
        return navigator!!
    }

    fun setNavigator(navigator: T) {
        this.navigator = navigator
    }

    fun logE(message: String?){
        Log.e(this.javaClass.simpleName, message?:"null")
    }

//    fun changeHasMaterialStatus(value: Boolean) {
//        hasMaterial.set(value)

//        if (!value) {
//            val dialog = CustomDialogContactUs()
//        }
//    }

//    fun changeDoScheduleStatus(value: Boolean) {
//        doSchedule.set(value)
//
//        if  (!value) {
//            selectScheduleType.set(false)
//        }
//    }
}