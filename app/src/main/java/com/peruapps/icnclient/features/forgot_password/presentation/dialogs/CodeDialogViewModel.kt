package com.peruapps.icnclient.features.forgot_password.presentation.dialogs

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.peruapps.icnclient.R
import com.peruapps.icnclient.features.forgot_password.data.ForgotPasswordRepository
import com.peruapps.icnclient.ui.base.BaseViewModel

class CodeDialogViewModel(private val repository: ForgotPasswordRepository) : BaseViewModel<CodeDialogNavigator>() {

    val code = ObservableField<String>()

    private val _validationMessage = MutableLiveData<Int>()
    val validationMessage: LiveData<Int>
        get() = _validationMessage

    private fun validator(): Boolean {
        val code = code.get()

        if (code == "" || code == null) {
            _validationMessage.value = R.string.validation_empty
            return false
        }

        return true
    }

    fun onClickSendButton() {
        val validator = validator()

        if (validator) {
            startJob {
                val response = repository.tokenValidation(code.get()!!)
                getNavigator().showResetPasswordView(response)
            }
        }
    }
}