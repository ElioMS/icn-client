package com.peruapps.icnclient.features.register.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.peruapps.icnclient.model.request.RegisterRequest
import com.peruapps.icnclient.room.repository.OnRegisterReadyCallback
import com.peruapps.icnclient.room.repository.RegisterRemoteDataSource

class RegisterViewModel: ViewModel() {

    var repository: RegisterRemoteDataSource = RegisterRemoteDataSource()

    var name = ObservableField("")
    var surname = ObservableField("")
    var age = ObservableField("")
    var gender = ObservableField("")
    var email = ObservableField("")
    var password = ObservableField("")
    var confirmPassword = ObservableField("")

    val isLoading = ObservableField(false)

    fun register() {
        isLoading.set(true)
        val user = RegisterRequest(
            name.get().toString(),
            surname.get().toString(),
            age.get().toString().toInt(),
            gender.get().toString(),
            email.get().toString(),
            password.get().toString(),
            confirmPassword.get().toString(),
            0,
            1)

        repository.createNewUser(user, object: OnRegisterReadyCallback {
            override fun onDataReady(data: String) {
                isLoading.set(false)
            }
        })
    }

}