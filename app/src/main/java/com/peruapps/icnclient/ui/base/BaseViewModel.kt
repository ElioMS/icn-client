package com.peruapps.icnclient.ui.base

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peruapps.icnclient.dialogs.CustomDialogContactUs
import com.peruapps.icnclient.features.services.views.ServicesNavigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.HttpException
import java.lang.ref.WeakReference
import java.util.logging.Handler


abstract class BaseViewModel<T> : ViewModel() {

    private var navigator: WeakReference<T>? = null
//    var requestLoading = MutableLiveData<Boolean>()
    var requestLoading = ObservableField<Boolean>(false)

    var hasMaterial = ObservableField<Boolean>()
    var doSchedule = ObservableField<Boolean>()
    var selectScheduleType = ObservableField<Boolean>()

    private val _showError = MutableLiveData("")
    val showError : LiveData<String>
        get() = _showError

    fun getNavigator(): T {
        return navigator?.get()!!
    }

    fun setNavigator(navigator: T) {
        this.navigator = WeakReference(navigator)
    }

    protected fun startJob(block: suspend () -> Unit): Job {
        return viewModelScope.launch {
            try {
                requestLoading.set(true)
                block()
            } catch (error: Throwable) {
                var oError = ""

                if (error is HttpException) {
                    oError = JSONObject(error.response()?.errorBody()!!.string()).optString("description")
                    Log.e("viewmodel", oError)
                }
//                val message = JSONObject(error.toString()).optString("message")
                _showError.value = oError

                Log.e("BaseViewModel", error.message ?: "")
            } finally {
                requestLoading.set(false)
            }
        }
    }

    fun logE(message: String?) {
        Log.e(this.javaClass.simpleName, message ?: "null")
    }
}