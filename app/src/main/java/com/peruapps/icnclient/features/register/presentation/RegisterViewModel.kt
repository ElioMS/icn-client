package com.peruapps.icnclient.features.register.presentation

import android.util.Log
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.peruapps.icnclient.features.register.data.RegisterRepository
import com.peruapps.icnclient.model.request.RegisterRequest

import com.peruapps.icnclient.ui.base.BaseViewModel
import java.io.File

class RegisterViewModel(private val repository: RegisterRepository) : BaseViewModel<RegisterNavigator>() {

    var name = ObservableField("")
    var surname = ObservableField("")
    var gender = ObservableField("")
    var documentType = ObservableField(1)
    var documentNumber = ObservableField("")
    var age = ObservableField("")
    var email = ObservableField("")
    var phone = ObservableField("")
    var address = ObservableField("")
    var addressReference = ObservableField("")
    var password = ObservableField("")
    var confirmPassword = ObservableField("")
    val loadedPicture = ObservableField<File>()

    fun onClickNextButton() {
        getNavigator().showPasswordView()
    }

    fun setGender(value: Int) {
        when (value) {
            1 -> gender.set("M")
            else -> gender.set("F")
        }
    }

    fun setDocumentType(value: Int) = documentType.set(value)


    private fun validator(): Boolean {
        val password = password.get()
        val confirmPassword = confirmPassword.get()

        if (password != confirmPassword) {
            return false
        }

        return true
    }

    fun register() {

//        validator()
        Log.d("name", name.get())
        startJob {
            val response = repository.createClientAccount(
                loadedPicture.get(),
                name = name.get()!!,
                surname = surname.get()!!,
                gender = gender.get()!!,
                documentType = documentType.get()!!,
                documentNumber = documentNumber.get()!!,
                age = age.get()!!.toInt(),
                email = email.get()!!,
                phone = phone.get()!!,
                address = address.get()!!,
                addressReference = addressReference.get(),
                password = password.get()!!)

            Log.d("REGISTER_RESPONSE", response.toString())

            getNavigator().showPasswordView()
        }
    }

}