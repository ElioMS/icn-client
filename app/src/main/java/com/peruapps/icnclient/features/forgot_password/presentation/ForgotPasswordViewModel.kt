package com.peruapps.icnclient.features.forgot_password.presentation

import android.content.res.Resources
import android.util.Patterns
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.peruapps.icnclient.R
import com.peruapps.icnclient.features.forgot_password.data.ForgotPasswordRepository
import com.peruapps.icnclient.ui.base.BaseViewModel

class ForgotPasswordViewModel (private val repository: ForgotPasswordRepository): BaseViewModel<ForgotPasswordNavigator>() {

    val email = ObservableField<String>("")

    private val _validationMessage = MutableLiveData<Int>()
    val validationMessage : LiveData<Int>
        get() = _validationMessage

    private fun validator(): Boolean {
        val email = email.get()

        if (email == "") {
            _validationMessage.value = R.string.validation_empty
            return false
        }

        if (email != "" && !Patterns.EMAIL_ADDRESS.matcher(email!!).matches()) {
            _validationMessage.value = R.string.validation_email
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