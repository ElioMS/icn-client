package com.peruapps.icnclient.features.reset_password.presentation

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.peruapps.icnclient.R
import com.peruapps.icnclient.features.reset_password.data.ResetPasswordRepository
import com.peruapps.icnclient.model.request.ResetPasswordRequest
import com.peruapps.icnclient.ui.base.BaseViewModel

class ResetPasswordViewModel (private val repository: ResetPasswordRepository): BaseViewModel<ResetPasswordNavigator>() {

    val token = ObservableField<String>("")
    val email = ObservableField<String>("")
    val password = ObservableField<String>("")
    val confirmPassword = ObservableField<String>("")

    private val _validationMessage = MutableLiveData<Int>()
    val validationMessage : LiveData<Int>
        get() = _validationMessage

    private fun validator(): Boolean {
        val password = password.get()
        val confirmPassword = confirmPassword.get()

        if (password == "") {
            _validationMessage.value = R.string.validation_empty
            return false
        }

        if  (password!!.contains(" ")) {
            _validationMessage.value = R.string.validation_blank_space
            return false
        }

        if (password != "" && password.length < 6) {
            _validationMessage.value = R.string.validation_password_min_characters
            return false
        }

        if (confirmPassword == "") {
            _validationMessage.value = R.string.validation_empty
            return false
        }

        val numRegex = "^(?=.*[0-9])(?=.*[a-zA-Z])([a-zA-Z0-9]+)\$".toRegex()
        val alphaRegex = ".*[A-Z].*".toRegex()

        if  ((!password.matches(numRegex)) || (!confirmPassword!!.matches(numRegex))) {
            _validationMessage.value = R.string.validation_alphanumeric_password
            return false
        }

        if (password != confirmPassword) {
            _validationMessage.value = R.string.validation_confirm_password
            return false
        }

        return true
    }

    fun onClickResetPassword() {
        val validator = validator()

        val data = ResetPasswordRequest(
            token = token.get()!!,
            email = email.get()!!,
            password = password.get()!!
        )

        if (validator) {
            startJob {
                repository.resetPassword(data)
                getNavigator().onSuccessResponse()
            }
        }
    }
}