package com.peruapps.icnclient.features.reset_password.presentation

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.peruapps.icnclient.features.reset_password.data.ResetPasswordRepository
import com.peruapps.icnclient.model.request.ResetPasswordRequest
import com.peruapps.icnclient.ui.base.BaseViewModel

class ResetPasswordViewModel (private val repository: ResetPasswordRepository): BaseViewModel<ResetPasswordNavigator>() {

    val token = ObservableField<String>("")
    val email = ObservableField<String>("")
    val password = ObservableField<String>("")
    val confirmPassword = ObservableField<String>("")

    private val _validationMessage = MutableLiveData<String>()
    val validationMessage : LiveData<String>
        get() = _validationMessage

    private fun validator(): Boolean {
        val password = password.get()
        val confirmPassword = confirmPassword.get()

        if (password == "") {
            _validationMessage.value = "El campo contraseña es obligatorio."
            return false
        }

        if (confirmPassword == "") {
            _validationMessage.value = "El campo confirmar contraseña es obligatorio."
            return false
        }

        if (password != confirmPassword) {
            _validationMessage.value = "Las contraseñas no coinciden."
            return false
        }

        return true
    }

    fun onClickResetPassword() {
        val validator = validator()

        val data = ResetPasswordRequest(token.get()!!,
            email.get()!!,
            password = password.get()!!)

        if (validator) {
            startJob {
                repository.resetPassword(data)
                getNavigator().onSuccessResponse()
            }
        }
    }
}