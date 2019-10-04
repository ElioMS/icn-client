package com.peruapps.icnclient.ui.base

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peruapps.icnclient.dialogs.CustomDialogContactUs
import com.peruapps.icnclient.features.services.views.ServicesNavigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.ref.WeakReference
import java.util.logging.Handler


abstract class BaseViewModel<T> : ViewModel() {

    private var navigator: WeakReference<T>? = null
//    var requestLoading = MutableLiveData<Boolean>()
    var requestLoading = ObservableField<Boolean>(false)

    var hasMaterial = ObservableField<Boolean>()
    var doSchedule = ObservableField<Boolean>()
    var selectScheduleType = ObservableField<Boolean>()

    fun getNavigator(): T {
        return navigator?.get()!!
    }

    fun setNavigator(navigator: T) {
        this.navigator = WeakReference(navigator)
    }

    protected fun startJob(block: suspend () -> Unit): Job {
        return viewModelScope.launch {
            try {
//                withContext(Dispatchers.Main) {
                    requestLoading.set(true)
//                }
                block()

            } catch (error: Throwable) {
                Log.e("BaseViewModel", error.message ?: "")
                // Display only our custom exception.
                // Other type of exception should only be printed.
                // Because other types can be for X reason we are not handled.
                /*if (error is XMLResponseException) {
                    _errorCause.value = error.message
                }*/
//                _showEmptyView.value = true
            } finally {
                requestLoading.set(false)
            }
        }
    }

    fun logE(message: String?) {
        Log.e(this.javaClass.simpleName, message ?: "null")
    }
}