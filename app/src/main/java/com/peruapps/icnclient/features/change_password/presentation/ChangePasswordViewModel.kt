package com.peruapps.icnclient.features.change_password.presentation

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.peruapps.icnclient.features.change_password.data.ChangePasswordRepository
import com.peruapps.icnclient.model.request.PasswordRequest
import com.peruapps.icnclient.ui.base.BaseViewModel
import java.util.*
import androidx.core.app.ActivityCompat.startActivityForResult
import android.provider.MediaStore
import androidx.core.content.FileProvider
import android.content.Intent



class ChangePasswordViewModel(
    private val repository: ChangePasswordRepository
) : BaseViewModel<ChangePasswordNavigator>() {

    val currentPassword = ObservableField<String>("")
    val newPassword = ObservableField<String>("")
    val confirmPassword = ObservableField<String>("")

    private val _validationMessage = MutableLiveData<String>()
    val validationMessage : LiveData<String>
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

        if (currentPassword == "") {
            _validationMessage.value = "Ingresar contraseña actual"
            return false
        }

        if (newPassword == "" || confirmPassword == "") {
            _validationMessage.value = "Ingresar nueva contraseña y/o confirmarla"
            return false
        }

        if (newPassword != confirmPassword) {
            _validationMessage.value = "Las contraseñas no coinciden"
            return false
        }

        return true
    }


}