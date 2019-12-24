package com.peruapps.icnclient.features.change_password.presentation

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.peruapps.icnclient.R
import com.peruapps.icnclient.features.change_password.data.ChangePasswordRepository
import com.peruapps.icnclient.model.request.PasswordRequest
import com.peruapps.icnclient.ui.base.BaseViewModel


class ChangePasswordViewModel(
    private val repository: ChangePasswordRepository
) : BaseViewModel<ChangePasswordNavigator>() {

    val currentPassword = ObservableField<String>("")
    val newPassword = ObservableField<String>("")
    val confirmPassword = ObservableField<String>("")

    private val _validationMessage = MutableLiveData<Int>()
    val validationMessage : LiveData<Int>
        get() = _validationMessage

    fun onSaveClicked() {
        if (validateForm()) {
            val data = PasswordRequest(currentPassword.get()!!, newPassword.get()!!)
            startJob {
                repository.changePassword(data)
                getNavigator().showSuccessToast()
//                Log.d("validation", response.toString())
            }
        }
    }

    private fun validateForm(): Boolean {
        val currentPassword = currentPassword.get()
        val newPassword = newPassword.get()
        val confirmPassword = confirmPassword.get()

        if (currentPassword == "" || newPassword == "" || confirmPassword == "") {
            _validationMessage.value = R.string.validation_empty
            return false
        }

        if (currentPassword?.length!! < 6 || newPassword?.length!! < 6 || confirmPassword?.length!! < 6) {
            _validationMessage.value = R.string.validation_password_min_characters
            return false
        }

        val regex = "^[a-zA-Z0-9]+\$".toRegex()

        if  (!newPassword.matches(regex) || !confirmPassword.matches(regex)) {
            _validationMessage.value = R.string.validation_alphanumeric_password
            return false
        }

        if (newPassword != confirmPassword) {
            _validationMessage.value = R.string.validation_confirm_password
            return false
        }

        return true
    }

    fun onClickNavigationBack() {
        getNavigator().navigationBack()
    }
}