package com.peruapps.icnclient.features.register.presentation

import android.util.Log
import android.util.Patterns
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.peruapps.icnclient.features.register.data.RegisterRepository
import com.peruapps.icnclient.model.request.RegisterRequest

import com.peruapps.icnclient.ui.base.BaseViewModel
import java.io.File

class RegisterViewModel(private val repository: RegisterRepository) : BaseViewModel<RegisterNavigator>() {

    val TAG = RegisterViewModel::class.java.simpleName

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

    private val _showMessage = MutableLiveData("")
    val showMessage: LiveData<String>
        get() = _showMessage

    fun onClickNextButton() {
//        val validator = infoValidator()

//        if (!validator.status) {
//            _showMessage.value = ""
            getNavigator().showPasswordView()
//        } else {
//            _showMessage.value = validator.message?.let { it } ?: run { "Por favor complete los campos vacíos" }
//        }
    }

    fun setGender(value: Int) {
        when (value) {
            1 -> gender.set("M")
            else -> gender.set("F")
        }
    }

    fun setDocumentType(value: Int) = documentType.set(value)

    private fun infoValidator(): ValidatorState {
        val name = name.get()
        val surname = surname.get()
        val gender = gender.get()
        val documentNumber = documentNumber.get()
        val age = age.get()
        val email = email.get()
        val phone = phone.get()
        val address = address.get()

        val validation = ValidatorState(true)

        if (name == "") { return validation }
        if (surname == "") { return validation }
        if (gender == "") { return validation }
        if (documentNumber == "") { return validation }
        if (age == "") { return validation }

        if (age !== "" && age?.toInt()!! < 18) {
            return validation.apply { message = "Menor de edad" }
        }

        if (email == "") { return  validation }

        if (email !== "" && !Patterns.EMAIL_ADDRESS.matcher(email!!).matches()) {
            return validation.apply { message = "Formato de correo incorrecto" }
        }

        if (phone == "") { return validation }
        if (address == "") { return validation }

        return ValidatorState(false)
    }

    private fun passwordValidator(): ValidatorState {
        val password = password.get()
        val confirmPassword = confirmPassword.get()

        val validation = ValidatorState(true)

        if (password == "") { return validation }

        if  (password != "" && password?.length!! < 6) {
            return validation.apply { message = "Mínimo de 6 caracteres" }
        }

        if (password != confirmPassword) {
            return validation.apply { message = "Las contraseñas deben coincidir" }
        }

        return ValidatorState(false)
    }

    fun register() {

        val validator = passwordValidator()

        if  (!validator.status) {
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
                    password = password.get()!!
                )

                Log.d("REGISTER_RESPONSE", response.toString())

                getNavigator().showPasswordView()
            }
        } else {
            _showMessage.value = validator.message?.let { it } ?: run { "Por favor complete los campos vacíos" }
        }
    }
}

data class ValidatorState(val status: Boolean, var message: String? = null)