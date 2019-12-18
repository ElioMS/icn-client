package com.peruapps.icnclient.features.forgot_password.presentation

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.peruapps.icnclient.features.forgot_password.data.ForgotPasswordRepository
import com.peruapps.icnclient.ui.base.BaseViewModel

class ForgotPasswordViewModel (private val repository: ForgotPasswordRepository): BaseViewModel<ForgotPasswordNavigator>() {

    val email = ObservableField<String>("")

    private val _validationMessage = MutableLiveData<String>()
    val validationMessage : LiveData<String>
        get() = _validationMessage

    private fun validator(): Boolean {
        val email = email.get()

        if (email == "") {
            _validationMessage.value = "El campo email se encuentra vacío."
            return false
        }

        return true
    }

    fun sendEmail() {

        val validator = validator()

        if (validator) {
            startJob {
                repository.tokenRequest(email.get()!!)
                getNavigator().sendEmail()
            }
        }
    }

    fun validateUserCode() {

    }

    fun resetPassword() {

    }

}